const express = require('express');
const cors = require('cors');
const path = require('path');
const catalogueRoutes = require('./routes/catalogueRoutes');
const catalogueController = require('./controllers/catalogueController');

const app = express();
const PORT = process.env.PORT || 5000;

app.use(cors());
app.use(express.json({ limit: '50mb' }));
app.use(express.urlencoded({ limit: '50mb', extended: true }));

// Serve static generated image files
app.use(express.static(path.join(__dirname, '../public')));

// API Routes
app.use('/api/v1/catalogue', catalogueRoutes);

// Health Check
app.get('/api/v1/health', catalogueController.healthCheck);
app.get('/health', catalogueController.healthCheck);

app.listen(PORT, () => {
    console.log(`[VASCS CLOUD AI BACKEND] Running securely on port ${PORT}`);
});
