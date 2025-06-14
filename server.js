// server.js
const port = 8080;
const express = require('express');
const path = require('path');
const app = express();

const PORT = process.env.PORT || 8080;
const DIST_DIR = path.join(__dirname, 'html', 'build', 'dist');

app.use(express.static(DIST_DIR));

// For single-page apps, fallback to index.html
app.get('*', (req, res) => {
  res.sendFile(path.join(DIST_DIR, 'index.html'));
});

app.listen(PORT, () => {
  console.log(`Server running at http://localhost:${PORT}/`);
});