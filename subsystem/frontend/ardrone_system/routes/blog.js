/**
 * Created by khjtony on 2/19/17.
 */
var express = require('express');
var router = express.Router();
var fs = require('fs');
var appRoot = require('app-root-path');
var path = require('path');
var marked = require('marked');
var app = express();
app.use(express.static(path.join(__dirname, 'public')));
// var filepath = path.join(appRoot.toString()+'/public/blogs/2017-02-14-New-blog.md');

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

function _res_blog(req,res){
    console.log('In res_blog, url param is: '+req.params.file);
    try{
        var filepath = path.join(appRoot.toString()+'/public/blogs/'+req.params.file+".md");
        console.log("I am reading this blog file: "+filepath.toString());
        fs.readFile(filepath, "utf-8", function(err, data){
            res.render('blog', {content:marked(data.toString())});
        })
        // res.render('blog', { content: marked(fs.readFileSync(filepath, "utf-8").toString())});
    }catch (e){
        console.log(e.message);
        console.log("My dir is: "+__dirname);
        console.log("File content is: "+fs.readFileSync(filepath));
        res.send("Cannot read file");
    }

}

/* GET home page. */
router.get('/:file', function(req, res) {
    _res_blog(req,res);
});

module.exports = router;


/*

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







 */