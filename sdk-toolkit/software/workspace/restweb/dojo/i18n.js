/*
	Copyright (c) 2004-2011, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/

//>>built
define("dojo/i18n",["./_base/kernel","require","./has","./_base/array","./_base/config","./_base/lang","./_base/xhr"],function(_1,_2,_3,_4,_5,_6,_7){var _8=_1.i18n={},_9=/(^.*(^|\/)nls)(\/|$)([^\/]*)\/?([^\/]*)/,_a=function(_b,_c,_d,_e){for(var _f=[_d+_e],_10=_c.split("-"),_11="",i=0;i<_10.length;i++){_11+=(_11?"-":"")+_10[i];if(!_b||_b[_11]){_f.push(_d+_11+"/"+_e);}}return _f;},_12={},_13=_1.getL10nName=function(_14,_15,_16){_16=_16?_16.toLowerCase():_1.locale;_14="dojo/i18n!"+_14.replace(/\./g,"/");_15=_15.replace(/\./g,"/");return (/root/i.test(_16))?(_14+"/nls/"+_15):(_14+"/nls/"+_16+"/"+_15);},_17=function(_18,_19,_1a,_1b,_1c,_1d){_18([_19],function(_1e){var _1f=_12[_19+"/"]=_6.clone(_1e.root),_20=_a(!_1e._v1x&&_1e,_1c,_1a,_1b);_18(_20,function(){for(var i=1;i<_20.length;i++){_12[_20[i]]=_1f=_6.mixin(_6.clone(_1f),arguments[i]);}var _21=_19+"/"+_1c;_12[_21]=_1f;_1d&&_1d(_6.delegate(_1f));});});},_22=function(id,_23){var _24=_9.exec(id),_25=_24[1];return /^\./.test(_25)?_23(_25)+"/"+id.substring(_25.length):id;},_26=function(id,_27,_28){var _29=_9.exec(id),_2a=_29[1]+"/",_2b=_29[5]||_29[4],_2c=_2a+_2b,_2d=(_29[5]&&_29[4]),_2e=_2d||_1.locale,_2f=_2c+"/"+_2e;if(_2d){if(_12[_2f]){_28(_12[_2f]);}else{_17(_27,_2c,_2a,_2b,_2e,_28);}return;}var _30=_5.extraLocale||[];_30=_6.isArray(_30)?_30:[_30];_30.push(_2e);var _31=_30.length,_32;_4.forEach(_30,function(_33){_17(_27,_2c,_2a,_2b,_33,function(_34){if(_33==_2e){_32=_34;}if(!--_31){_28(_32);}});});};true||_3.add("dojo-v1x-i18n-Api",1);if(1){var _35=new Function("bundle","var __preAmdResult, __amdResult; function define(bundle){__amdResult= bundle;} __preAmdResult= eval(bundle); return [__preAmdResult, __amdResult];"),_36=function(url,_37,_38){return _37?(/nls\/[^\/]+\/[^\/]+$/.test(url)?_37:{root:_37,_v1x:1}):_38;},_39=function(_3a,_3b){var _3c=[];_4.forEach(_3a,function(mid){var url=_2.toUrl(mid+".js");if(_12[url]){_3c.push(_12[url]);}else{try{var _3d=_2(mid);if(_3d){_3c.push(_3d);return;}}catch(e){}_7.get({url:url,sync:true,load:function(_3e){var _3f=_35(_3e);_3c.push(_12[url]=_36(url,_3f[0],_3f[1]));},error:function(){_3c.push(_12[url]={});}});}});_3b.apply(null,_3c);};_8.getLocalization=function(_40,_41,_42){var _43,_44=_13(_40,_41,_42).substring(10);_26(_44,(1&&!_2.isXdUrl(_2.toUrl(_44+".js"))?_39:_2),function(_45){_43=_45;});return _43;};_8.normalizeLocale=function(_46){var _47=_46?_46.toLowerCase():_1.locale;if(_47=="root"){_47="ROOT";}return _47;};}return _6.mixin(_8,{dynamic:true,normalize:_22,load:_26,cache:function(mid,_48){_12[mid]=_48;}});});