/**
 * Created by khjtony on 2/16/17.
 */

var express = require('express');
var router = express.Router();
var fs = require('fs');
var appRoot = require('app-root-path');
var path = require('path');
var banner_path = path.join(appRoot.toString(), "/public/images/banner/");

function _fresh_banner(res){
    var newline="";
    fs.readdir(banner_path, function (err, items) {
        console.log(items);
        var banners= (items.length>5?5:items.length);
        for (var i = 0; i < banners; i++) {
            newline += items[Math.floor(Math.random() * banners)];
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