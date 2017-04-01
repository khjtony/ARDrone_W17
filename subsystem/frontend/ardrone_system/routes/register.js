const express = require('express');
const Account = require('../models/account');
var router = express.Router();
var passport = require('passport');


router.get('/register', function(req, res){
    res.render('register', { });
});

router.post('/register', function(req, res, next){
    Account.register(new Account({ username : req.body.username }), req.body.password, function(err, account){
        if (err) {
            return res.render('register', { error : err.message });
        }

        passport.authenticate('local',function(req, res){
            req.session.save(function(err){
                if (err) {
                    return next(err);
                }
                res.redirect('/');
            });
        });
    });
});

module.exports = router;