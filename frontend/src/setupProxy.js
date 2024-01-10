const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function (app) {
  app.use(
    '/users',
    createProxyMiddleware({
      target: 'http://127.0.0.1/',
      //target: 'http://localhost:8082/',
      changeOrigin: true,
    })
  );
};