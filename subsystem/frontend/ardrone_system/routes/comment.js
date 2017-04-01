var express = require('express');
var router = express.Router();

router.param('comment', function(req, res, next, comment){
    console.log("The comment is: " + comment);
    req.comment = comment;
    next();
});

router.get('/comment/:comment', function(req, res){
    res.send('hello ' + req.comment);
});

module.exports = router;