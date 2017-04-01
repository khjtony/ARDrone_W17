var express = require('express');
var path = require('path');
var passport = require('passport');
var favicon = require('serve-favicon');
var logger = require('morgan');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');
var mongoose = require('mongoose');
var LocalStrategy = require('passport-local').Strategy;
var flash = require('connect-flash');
var marked = require('marked');
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

var app = express();


//setup router
var login = require('./routes/login');
var index = require('./routes/index');
var users = require('./routes/users');
var contact = require('./routes/contact');
var profile = require('./routes/profile');
var blog = require('./routes/blog');
var fresh_blog = require('./routes/fresh_blog');        // poll fresh blogs
var fresh_banner = require('./routes/fresh_banner');        // poll fresh banners
var comment = require('./routes/comment');
var register = require('./routes/register');
var dashboard = require('./routes/dashboard');
//end setup router

// setup parser and passport
app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(require('express-session')({
    secret: 'keyboard cat',
    resave: false,
    saveUninitialized: false
}));
app.use(passport.initialize());
app.use(flash());
app.use(passport.session());
app.use(express.static(path.join(__dirname, 'public')));
// passport config-
var Account = require('./models/account');
passport.use(new LocalStrategy(Account.authenticate()));
passport.serializeUser(Account.serializeUser());
passport.deserializeUser(Account.deserializeUser());

// mongoose
mongoose.connect('mongodb://localhost/passport_local_mongoose_express4');




// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'hbs');


// setup routers
app.get('/flash', function(req, res){
    // Set a flash message by passing the key, followed by the value, to req.flash().
    req.flash('info', 'Flash is back!')
    res.redirect('/');
});
app.use('/', index);
app.use('/', dashboard);
app.use('/',login);
app.use('/', register);
app.use('/', users);
app.use('/contact',contact);
app.use('/profile',profile);
app.use('/blog', blog);
app.use('/fresh_blog',fresh_blog);
app.use('/fresh_banner',fresh_banner);






// catch 404 and forward to error handler
app.use(function(req, res, next) {
  var err = new Error('Not Found');
  err.status = 404;
  next(err);
});

// error handler
app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.render('error');
});

module.exports = app;

app.listen(3000,'0.0.0.0');

