const { v4: uuidv4 } = require('uuid');
const aiProviderAdapter = require('./aiProviderAdapter');

const jobs = new Map();

exports.createJob = (requestData, hostUrl = '') => {
    const jobId = `job-${uuidv4()}`;
    const job = {
        jobId,
        productId: requestData.productId,
        sourceImageUri: requestData.sourceImageUri,
        sourceImageBase64: requestData.sourceImageBase64,
        style: requestData.style || 'BRIDAL',
        modelId: requestData.modelId || 'model-standard-01',
        backgroundStyle: requestData.backgroundStyle || 'Showroom Studio',
        pose: requestData.pose || 'Standing Elegance',
        resolution: requestData.resolution || '1024x1536',
        prompt: requestData.prompt,
        negativePrompt: requestData.negativePrompt,
        status: 'QUEUED',
        progress: 10,
        resultImageUri: null,
        errorCode: null,
        errorMessage: null,
        createdAt: new Date().toISOString(),
        updatedAt: new Date().toISOString()
    };

    jobs.set(jobId, job);

    // Execute REAL AI Provider generation asynchronously
    setImmediate(async () => {
        const currentJob = jobs.get(jobId);
        if (!currentJob || currentJob.status === 'CANCELLED') return;

        currentJob.status = 'PROCESSING';
        currentJob.progress = 40;
        currentJob.updatedAt = new Date().toISOString();

        try {
            const providerResult = await aiProviderAdapter.generateSareeCatalogueImage(currentJob);

            if (currentJob.status === 'CANCELLED') return;

            if (providerResult.success) {
                currentJob.status = 'SUCCESS';
                currentJob.progress = 100;
                currentJob.resultImageUri = hostUrl
                    ? `${hostUrl}${providerResult.generatedImageUrl}`
                    : providerResult.generatedImageUrl;
                currentJob.updatedAt = new Date().toISOString();
            } else {
                currentJob.status = 'FAILED';
                currentJob.progress = 0;
                currentJob.errorCode = providerResult.errorCode || 'GENERATION_FAILED';
                currentJob.errorMessage = providerResult.errorMessage || 'AI catalogue generation failed.';
                currentJob.updatedAt = new Date().toISOString();
            }
        } catch (err) {
            if (currentJob.status === 'CANCELLED') return;
            currentJob.status = 'FAILED';
            currentJob.progress = 0;
            currentJob.errorCode = 'SERVER_EXCEPTION';
            currentJob.errorMessage = `Unhandled server error: ${err.message}`;
            currentJob.updatedAt = new Date().toISOString();
        }
    });

    return job;
};

exports.getJob = (jobId) => {
    return jobs.get(jobId);
};

exports.cancelJob = (jobId) => {
    const job = jobs.get(jobId);
    if (!job) return false;
    if (job.status === 'SUCCESS' || job.status === 'FAILED') return false;

    job.status = 'CANCELLED';
    job.progress = 0;
    job.errorMessage = 'Job cancelled by user request.';
    job.updatedAt = new Date().toISOString();
    return true;
};
