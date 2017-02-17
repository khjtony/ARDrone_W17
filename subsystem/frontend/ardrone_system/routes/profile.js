/**
 * Created by khjtony on 2/14/17.
 */
var express = require('express');
var appRoot = require('app-root-path');
var path = require('path');
var router = express.Router();
var marked = require('marked');
var app=express();
app.use(express.static(path.join(__dirname, 'public')));
marked.setOptions({
    renderer: new marked.Renderer(),
    gfm: true,
    tables: true,
    breaks: false,
    pedantic: false,
    sanitize: true,
    smartLists: true,
    smartypants: false
});
var fs = require('fs');
var filepath = path.join(appRoot.toString()+'/public/blogs/2017-02-14-New-blog.md');


/* GET profile page. */
router.get('/', function(req, res, next) {
    try{
        res.render('profile', { content: marked(fs.readFileSync(filepath, "utf-8").toString())});
    }catch (e){
        console.log(e.message);
        console.log("My dir is: "+__dirname);
        console.log("File content is: "+fs.readFileSync(filepath));
        res.send("Cannot read file");
    }
    console.log("GET /profile ");
});

module.exports = router;
