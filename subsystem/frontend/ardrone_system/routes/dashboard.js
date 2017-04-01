var express = require('express');
var router = express.Router();


// main
router.get('/dashboard', function(req, res, next){
    if(req.user){
        res.render('dashboard',{user: req.user.username});
    }else{
        res.redirect('/login');
    }
});

module.exports = router;