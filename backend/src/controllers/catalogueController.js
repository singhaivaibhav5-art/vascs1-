const jobManager = require('../services/jobManagerService');

const STYLES = [
    { id: 'BRIDAL', name: 'Bridal Showroom', description: 'Royal grand showroom setting with chandelier ambient lighting and bridal pose.' },
    { id: 'FESTIVE', name: 'Festive Collection', description: 'Warm festive celebration ambiance with subtle floral decor backdrop.' },
    { id: 'PARTY_WEAR', name: 'Party Wear Glamour', description: 'Modern sleek studio lighting suited for contemporary party sarees.' },
    { id: 'TRADITIONAL', name: 'Traditional Heritage', description: 'Classic South/North Indian heritage architectural courtyard setting.' },
    { id: 'OFFICE_WEAR', name: 'Office & Casual Wear', description: 'Clean minimalist high-key studio setting for daily and formal wear.' }
];

const MODELS = [
    { modelId: 'model-standard-01', name: 'Standard Indian Model 01', provider: 'Gemini Vision', styles: ['BRIDAL', 'FESTIVE', 'PARTY_WEAR', 'TRADITIONAL', 'OFFICE_WEAR'] },
    { modelId: 'model-royal-02', name: 'Royal Heritage Model 02', provider: 'Gemini Vision', styles: ['BRIDAL', 'TRADITIONAL'] },
    { modelId: 'model-modern-03', name: 'Contemporary Glamour Model 03', provider: 'Gemini Vision', styles: ['PARTY_WEAR', 'OFFICE_WEAR'] }
];

exports.submitGeneration = async (req, res) => {
    try {
        const {
            productId,
            sourceImageUri,
            sourceImageBase64,
            style,
            modelId,
            backgroundStyle,
            pose,
            resolution,
            prompt,
            negativePrompt
        } = req.body;

        if (!productId) {
            return res.status(400).json({ error: 'productId is required' });
        }

        const hostUrl = `${req.protocol}://${req.get('host')}`;

        const job = jobManager.createJob({
            productId,
            sourceImageUri,
            sourceImageBase64,
            style: style || 'BRIDAL',
            modelId: modelId || 'model-standard-01',
            backgroundStyle,
            pose,
            resolution,
            prompt,
            negativePrompt
        }, hostUrl);

        res.status(202).json({
            status: 'ACCEPTED',
            jobId: job.jobId,
            message: 'Catalogue generation job queued for real AI provider processing',
            job
        });
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
};

exports.getJobStatus = async (req, res) => {
    try {
        const { jobId } = req.params;
        const job = jobManager.getJob(jobId);
        if (!job) {
            return res.status(404).json({ error: 'Job not found' });
        }
        res.json(job);
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
};

exports.cancelJob = async (req, res) => {
    try {
        const { jobId } = req.params;
        const cancelled = jobManager.cancelJob(jobId);
        if (!cancelled) {
            return res.status(400).json({ error: 'Job could not be cancelled or does not exist.' });
        }
        res.json({ message: 'Job cancelled successfully', jobId });
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
};

exports.getStyles = (req, res) => {
    res.json(STYLES);
};

exports.getModels = (req, res) => {
    res.json(MODELS);
};

exports.healthCheck = (req, res) => {
    const keyConfigured = !!(process.env.AI_API_KEY || process.env.GEMINI_API_KEY || process.env.GOOGLE_API_KEY);
    res.json({
        status: 'ONLINE',
        system: 'VASCS Enterprise Cloud AI Saree Catalogue Server',
        aiProvider: process.env.AI_PROVIDER || 'gemini',
        aiModel: process.env.AI_MODEL_NAME || 'gemini-2.5-flash-image',
        aiProviderConfigured: keyConfigured,
        timestamp: new Date().toISOString()
    });
};
