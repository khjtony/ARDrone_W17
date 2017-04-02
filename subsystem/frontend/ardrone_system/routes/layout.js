var express = require('express');
var router = express.Router();

router.get('/', function(req, res, next){
    // if(req.user){
    //     res.renderPartials({user:req.user.username});
    // }
    next();
});

module.exports = router;