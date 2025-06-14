// server.js
const express = require('express');
const path = require('path');
const app = express();

const PORT = process.env.PORT || 8080;

// Change: navigate from current directory -> html -> build -> dist
const DIST_DIR = path.resolve(__dirname,'..', 'html', 'build', 'dist');

app.use(express.static(DIST_DIR));

// For single-page apps (SPA), fallback to index.html for all unmatched routes
app.get('*', (req, res) => {
  res.sendFile(path.join(DIST_DIR, 'index.html'));
});

app.listen(PORT, () => {
  console.log(`Server running at http://localhost:${PORT}/`);
});
