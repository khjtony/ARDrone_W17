var express = require('express');
var router = express.Router();
var middleware = require('./middleware.js');

/* GET users listing. */
router.get('/users', function(req, res, next) {
  res.send('respond with a resource');
});

module.exports = router;
