/**
 * Created by khjtony on 2/16/17.
 */

var express = require('express');
var router = express.Router();
var fs = require('fs');
var appRoot = require('app-root-path');
var path = require('path');
var banner_path = path.join(appRoot.toString(), "/public/images/banner/");

//http://stackoverflow.com/questions/6274339/how-can-i-shuffle-an-array
function shuffle(a) {
    var j, x, i;
    for (i = a.length; i; i--) {
        j = Math.floor(Math.random() * i);
        x = a[i - 1];
        a[i - 1] = a[j];
        a[j] = x;
    }
    return a;
}


function _fresh_banner(res){
    var newline="";
    fs.readdir(banner_path, function (err, items) {
        //console.log(items);
        items = shuffle(items);
        // push item in array then shuffle
        var limit= (items.length>5?5:items.length);
        for (var i = 0; i < limit; i++) {
            newline += items[i];
            newline += "\n";
        }
        res.json({banner:newline});
    });
}

/* GET home page. */
router.get('/', function(req, res) {
    _fresh_banner(res);
});

module.exports = router;