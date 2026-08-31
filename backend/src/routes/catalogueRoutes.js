const express = require('express');
const router = express.Router();
const catalogueController = require('../controllers/catalogueController');

// Submit generation request
router.post('/generate', catalogueController.submitGeneration);

// Get job status
router.get('/jobs/:jobId', catalogueController.getJobStatus);

// Cancel job
router.post('/jobs/:jobId/cancel', catalogueController.cancelJob);

// Get available draping styles
router.get('/styles', catalogueController.getStyles);

// Get available AI model configs
router.get('/models', catalogueController.getModels);

module.exports = router;
