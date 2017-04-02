var express = require('express');
var router = express.Router();
var passport = require('passport');

router.get('/login', function(req, res){
    res.render('login', { user : req.user, error : req.flash('error')});
});

router.post('/login', passport.authenticate('local', { failureRedirect: '/login', failureFlash: true }), function(req, res, next) {
    console.log("The user: "+req.user);
    req.session.save(function(err){
        if (err) {
            console.log("The user: "+req.user+" has error");
            return next(err);
        }

        res.redirect('/dashboard');
    });
});

router.get('/logout', function(req, res, next){
    req.logout();
    req.session.save(function(err){
        if(err){
            return next(err);
        }
        res.redirect('/');
    });
});

module.exports = router;