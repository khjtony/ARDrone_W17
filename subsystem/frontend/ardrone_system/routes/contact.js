/**
 * Created by khjtony on 2/14/17.
 */
var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {
    res.render('contact', { title: 'Contact Us' });
    console.log("GET /contact here");
});

module.exports = router;
