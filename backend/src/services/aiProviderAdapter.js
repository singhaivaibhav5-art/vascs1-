const https = require('https');
const fs = require('fs');
const path = require('path');

const OUTPUT_DIR = path.join(__dirname, '../../public/generated_catalogues');

// Ensure output storage directory exists
if (!fs.existsSync(OUTPUT_DIR)) {
    fs.mkdirSync(OUTPUT_DIR, { recursive: true });
}

/**
 * Real Gemini API Provider Adapter for Saree-to-Model Image Generation
 */
exports.generateSareeCatalogueImage = async (job) => {
    const apiKey = process.env.AI_API_KEY || process.env.GEMINI_API_KEY || process.env.GOOGLE_API_KEY;
    const modelName = process.env.AI_MODEL_NAME || 'gemini-2.5-flash-image';
    const baseUrl = process.env.AI_API_BASE_URL || 'https://generativelanguage.googleapis.com';

    if (!apiKey) {
        return {
            success: false,
            errorCode: 'MISSING_API_KEY',
            errorMessage: 'REAL AI GENERATION BLOCKED: Backend server missing AI_API_KEY environment variable configuration.'
        };
    }

    // Build high-precision preserve prompt
    const promptParts = [
        `Professional high-fashion Indian studio photo of a female fashion model wearing the exact saree shown in the reference photo.`,
        `Preserve exact saree colour, intricate border design, pallu motif, weave texture, and pattern identity without redesigning or altering the saree.`,
        `Draping Style: ${job.style || 'BRIDAL'}.`,
        `Model Posture: ${job.pose || 'Standing Elegance'}.`,
        `Background & Lighting: ${job.backgroundStyle || 'Showroom Studio'} with luxury ambient illumination.`
    ];

    if (job.prompt && job.prompt.trim()) {
        promptParts.push(`Additional Instructions: ${job.prompt.trim()}`);
    }

    const fullPrompt = promptParts.join(' ');

    // Prepare contents payload
    const parts = [{ text: fullPrompt }];

    // If source image base64 is provided
    if (job.sourceImageBase64) {
        let cleanBase64 = job.sourceImageBase64;
        let mimeType = 'image/jpeg';
        if (cleanBase64.includes(';base64,')) {
            const split = cleanBase64.split(';base64,');
            mimeType = split[0].replace('data:', '') || 'image/jpeg';
            cleanBase64 = split[1];
        }
        parts.push({
            inlineData: {
                mimeType: mimeType,
                data: cleanBase64
            }
        });
    }

    const requestBody = JSON.stringify({
        contents: [{ parts }],
        generationConfig: {
            responseModalities: ['IMAGE'],
            imageConfig: {
                aspectRatio: '3:4'
            }
        }
    });

    const apiUrl = `${baseUrl}/v1beta/models/${modelName}:generateContent?key=${apiKey}`;

    return new Promise((resolve) => {
        const req = https.request(apiUrl, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Content-Length': Buffer.byteLength(requestBody)
            },
            timeout: 60000
        }, (res) => {
            let responseData = '';
            res.on('data', chunk => { responseData += chunk; });
            res.on('end', () => {
                let jsonResponse;
                try {
                    jsonResponse = JSON.parse(responseData);
                } catch (e) {
                    return resolve({
                        success: false,
                        errorCode: 'INVALID_PROVIDER_RESPONSE',
                        errorMessage: `Failed to parse AI provider response: ${responseData.substring(0, 200)}`
                    });
                }

                if (res.statusCode === 200) {
                    const candidate = jsonResponse.candidates?.[0];
                    const imagePart = candidate?.content?.parts?.find(p => p.inlineData);

                    if (imagePart && imagePart.inlineData && imagePart.inlineData.data) {
                        const imgBuffer = Buffer.from(imagePart.inlineData.data, 'base64');
                        const fileName = `${job.jobId}.png`;
                        const filePath = path.join(OUTPUT_DIR, fileName);
                        fs.writeFileSync(filePath, imgBuffer);

                        const relativeUrl = `/generated_catalogues/${fileName}`;
                        return resolve({
                            success: true,
                            generatedImageUrl: relativeUrl,
                            fileName
                        });
                    } else {
                        return resolve({
                            success: false,
                            errorCode: 'NO_IMAGE_IN_RESPONSE',
                            errorMessage: 'AI Provider responded successfully but did not return image data in candidate content.'
                        });
                    }
                } else if (res.statusCode === 429) {
                    const providerMsg = jsonResponse.error?.message || 'Quota exceeded';
                    return resolve({
                        success: false,
                        errorCode: 'PROVIDER_QUOTA_EXHAUSTED',
                        errorMessage: `REAL AI GENERATION BLOCKED: ${providerMsg}`
                    });
                } else {
                    const providerMsg = jsonResponse.error?.message || `HTTP ${res.statusCode}`;
                    return resolve({
                        success: false,
                        errorCode: `PROVIDER_ERROR_${res.statusCode}`,
                        errorMessage: `AI Provider Error (${res.statusCode}): ${providerMsg}`
                    });
                }
            });
        });

        req.on('timeout', () => {
            req.destroy();
            resolve({
                success: false,
                errorCode: 'PROVIDER_TIMEOUT',
                errorMessage: 'AI Provider request timed out after 60 seconds.'
            });
        });

        req.on('error', (err) => {
            resolve({
                success: false,
                errorCode: 'NETWORK_ERROR',
                errorMessage: `Connection error calling AI Provider: ${err.message}`
            });
        });

        req.write(requestBody);
        req.end();
    });
};
