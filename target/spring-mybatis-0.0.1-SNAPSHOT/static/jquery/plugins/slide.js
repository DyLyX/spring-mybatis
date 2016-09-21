// Download by http://sc.xueit.com

var flag=0;
var Msg_loopNum = 8;
function showMsg(){
if (typeof(redmsg)!= 'undefined') {
if (!redmsg || redmsg.length < 1) { return; }
document.getElementById('mnum').innerHTML = redmsg;
}
else
{
if(Msg_loopNum > 0) {setTimeout(function(){showMsg();},100);Msg_loopNum--;}
}
}
function g(){var ls=location.search;if(ls.indexOf("q=")!=-1){try{var q=(ls.match(new RegExp("q=[^&$]*")).toString());document.f1.word.value=decodeURIComponent(q.substr(2));}catch(e){}}}
function s(o,p){if(document.f1.word.value.length>0){var oh=o.href;var wd=encodeURIComponent(document.f1.word.value);if(oh.indexOf("q=")!=-1){o.href=oh.replace(new RegExp("q=[^&$]*"),"q="+wd);}else{var s=p?"&":"?";o.href=o.href+s+"q="+wd};}}
function cutText(jsonText,ffOnly) {
if (ffOnly) { 
if (typeof(document.getElementsByTagName('body')[0].style.textOverflow)!='undefined' && typeof(window.opera)!='undefined') { return; }
}
for (var name in jsonText) {
var length=jsonText[name];
var elems=document.getElementsByName(name);
for (var i=elems.length-1; i>=0; i--) {
if (elems[i].scrollWidth>length) { elems[i].innerHTML+="..."; }
while (elems[i].scrollWidth>length) {
elems[i].innerHTML=elems[i].innerHTML.substr(0,elems[i].innerHTML.length-4)+"...";
}
}
}
}
function formatPic(jsonPic){ 
for (var name in jsonPic) {
var maxWidth=jsonPic[name].width;
var maxHeight=jsonPic[name].height;
var valign=jsonPic[name].valign;
var image=document.getElementsByName(name);
for(var i=0;i<image.length;i++)
{
if(image[i].width>0 && image[i].height>0){ 
var rate = (maxWidth/image[i].width < maxHeight/image[i].height)?maxWidth/image[i].width:maxHeight/image[i].height;
if(rate <= 1){      
image[i].width = image[i].width*rate;
if (valign==1) { image[i].style.marginTop = (maxHeight-image[i].height)/2 + "px"; }
}
} 
}
}
}
var sid=((new Date()).getTime()+"_"+Math.round(Math.random()*2147483637));
function sendPfScrollStat() { if(document.images){(new Image()).src='/js/blank.js?func=pfscroll&sid='+sid+'&t='+(new Date()).getTime();} }

//Resources from http://down.liehuo.net

var Slider=(function() {
var speed = 12;
var space = 8;
var slideWidth = 224;
var moveLock = false;
var moveTimer;
var dist = 0;
var slideTimer=null;
var isScroll = false;
var holder1 = G("holder1");
var slidePic = G("slidePic");
G("holder2").innerHTML = holder1.innerHTML;
slidePic.scrollLeft = holder1.scrollWidth;
function slidePlay(){
clearInterval(slideTimer);
slideTimer = setInterval(function(){slideDown();slideStopDown();},3000);
}
function slideStop(){
clearInterval(slideTimer);
}
function slideUp(){
if(moveLock) return;
clearInterval(slideTimer);
moveLock = true;
moveTimer = setInterval(function(){scrollUp();},speed);
}
function slideStopUp(){
if(isScroll);
clearInterval(moveTimer);
if(slidePic.scrollLeft % slideWidth != 0){
dist = -(slidePic.scrollLeft % slideWidth);
setDist();
}else{
moveLock = false;
}
slidePlay();
}
function scrollUp(){
if(slidePic.scrollLeft <= 0){slidePic.scrollLeft = holder1.offsetWidth}
slidePic.scrollLeft -= space;
}
function slideDown(){
clearInterval(moveTimer);
if(moveLock) return;
clearInterval(slideTimer);
moveLock = true;
scrollDown();
moveTimer = setInterval(function(){scrollDown();},speed);
}
function slideStopDown(){
if(isScroll);
clearInterval(moveTimer);
if(slidePic.scrollLeft % slideWidth != 0 ){
dist = slideWidth - slidePic.scrollLeft % slideWidth;
setDist();
}else{
moveLock = false;
}
slidePlay();
}
function scrollDown(){
if(slidePic.scrollLeft >= holder1.scrollWidth){slidePic.scrollLeft = 0;}
slidePic.scrollLeft += space ;
}
function setDist(){
if(dist == 0){
moveLock = false;
isScroll = false;
return;
}
var num;
var tempSpeed = speed,tempDist = space;
if(Math.abs(dist)<slideWidth/5){
tempDist =  Math.round(Math.abs(dist/5));
if(tempDist<1){tempDist=1};
}
if(dist < 0){
if(dist < -tempDist){
dist += tempDist;
num = tempDist;
}else{
num = -dist;
dist = 0;
}
slidePic.scrollLeft -= num;
setTimeout(function(){setDist();},tempSpeed);
}else{
if(dist > tempDist){
dist -= tempDist;
num = tempDist;
}else{
num = dist;
dist = 0;
}
slidePic.scrollLeft += num;
setTimeout(function(){setDist();},tempSpeed);
}
}

function init() {
var p=G('slidePic');
p.onmouseover=function(){Slider.stop();};
p.onmouseout=function(){Slider.play();};
var l=G('slide_left'),r=G('slide_right');
l.onmouseover=r.onmouseover=function(){Slider.stop();this.className='arr_bg';};
l.onmouseout=r.onmouseout=function(){this.className='';Slider.play();};
var lb=G('arrow_left');
lb.onmousedown=function(){this.className='arr_left';Slider.up();};
lb.onmouseup=function(){Slider.stopUp();};
lb.onmouseout=function(){Slider.stopUp();this.className='';};
var rb=G('arrow_right');
rb.onmousedown=function(){this.className='arr_right';Slider.down();};
rb.onmouseup=function(){Slider.stopDown();};
rb.onmouseout=function(){Slider.stopDown();this.className='';};
lb.onclick=rb.onclick=function(){setTimeout(sendPfScrollStat,1000);};
slidePlay();
}
return {
init:init,
play:slidePlay,
stop:slideStop,
up:slideUp,
stopUp:slideStopUp,
down:slideDown,
stopDown:slideStopDown
}
})();
Slider.init();
