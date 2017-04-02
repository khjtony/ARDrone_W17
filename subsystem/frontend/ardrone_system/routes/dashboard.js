var express = require('express');
var router = express.Router();


// main
router.get('/dashboard', function(req, res, next){
    if(req.user){
        console.log("I am in the dashboard: "+req.user.username);
        res.render('dashboard',{user: req.user.username});
        // res.render('chatroom', {user:req.user.username});
    }else{
        res.redirect('/login');
    }
});

//chat room
var chatRoom = function(req, res){
    // fetch chat history from mongodb
    return exphbs.render('chatroom', {user:req.user.username});
};

module.exports = router;