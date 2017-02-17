/**
 * Created by khjtony on 2/16/17.
 */

var express = require('express');
var router = express.Router();
var fs = require('fs');
var appRoot = require('app-root-path');
var path = require('path');
var blog_path = path.join(appRoot.toString(), "/public/blogs/");

function _fresh_blog(res){
    var newline="";
    fs.readdir(blog_path, function (err, items) {
        console.log(items);
        for (var i = 0; i < (items.length>5?5:items.length); i++) {
            newline += "<li>" + items[i].split('.')[0].split('-').slice(3).join(' ') + "</li>";
        }
        newline += "\n";
        newline += "<li><a href='#' style='color:deepskyblue;'>More...</a></li>";
        res.json({blog:newline});
    });
}

/* GET home page. */
router.get('/', function(req, res) {
    _fresh_blog(res);
});

module.exports = router;