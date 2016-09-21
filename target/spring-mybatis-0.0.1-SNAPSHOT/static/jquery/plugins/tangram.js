// JavaScript Document
// Download by http://sc.xueit.com
window.baidu = window.baidu || {
	version: "1-0-0",
	emptyFn: function() {}
};
baidu.isString = function(A) {
	return (typeof A == "object" && A && A.constructor == String) || typeof A == "string"
};
baidu.G = function() {
	for (var A = [], B = arguments.length - 1; B > -1; B--) {
		var C = arguments[B];
		A[B] = null;
		if (typeof C == "object" && C && C.dom) {
			A[B] = C.dom
		} else {
			if ((typeof C == "object" && C && C.tagName) || C == window || C == document) {
				A[B] = C
			} else {
				if (baidu.isString(C) && (C = document.getElementById(C))) {
					A[B] = C
				}
			}
		}
	}
	return A.length < 2 ? A[0] : A
};
baidu.getCurrentStyle = function(A, C) {
	var E = null;
	if (! (A = baidu.G(A))) {
		return null
	}
	if (E = A.style[C]) {
		return E
	} else {
		if (A.currentStyle) {
			E = A.currentStyle[C]
		} else {
			var D = A.nodeType == 9 ? A: A.ownerDocument || A.document;
			if (D.defaultView && D.defaultView.getComputedStyle) {
				var B = D.defaultView.getComputedStyle(A, "");
				if (B) {
					E = B[C]
				}
			}
		}
	}
	return E
};
baidu.trim = function(B, A) {
	if (A == "left") {
		return B.replace(/(^[\s\t\xa0\u3000]+)/g, "")
	}
	if (A == "right") {
		return B.replace(new RegExp("[\\u3000\\xa0\\s\\t]+\x24", "g"), "")
	}
	return B.replace(new RegExp("(^[\\s\\t\\xa0\\u3000]+)|([\\u3000\\xa0\\s\\t]+\x24)", "g"), "")
};
baidu.addClass = function(A, B) {
	if (! (A = baidu.G(A))) {
		return null
	}
	B = baidu.trim(B);
	if (!new RegExp("(^| )" + B.replace(/(\W)/g, "\\\x241") + "( |\x24)").test(A.className)) {
		A.className = baidu.trim(A.className.split(/\s+/).concat(B).join(" "))
	}
};
baidu.ac = baidu.addClass;
baidu.extend = function(C, A) {
	if (C && A && typeof(A) == "object") {
		for (var B in A) {
			C[B] = A[B]
		}
	}
	return C
};
baidu.browser = baidu.browser || {}; (function() {
	var A = navigator.userAgent;
	baidu.firefox = baidu.browser.firefox = /firefox\/(\d+\.\d)/i.test(A) ? parseFloat(RegExp["\x241"]) : 0;
	baidu.ie = baidu.browser.ie = /msie (\d+\.\d)/i.test(A) ? parseFloat(RegExp["\x241"]) : 0;
	baidu.opera = baidu.browser.opera = /opera\/(\d+\.\d)/i.test(A) ? parseFloat(RegExp["\x241"]) : 0;
	baidu.safari = baidu.browser.safari = (/(\d+\.\d)(\.\d)?\s+safari/i.test(A) && !/chrome/i.test(A)) ? parseFloat(RegExp["\x241"]) : 0;
	try {
		baidu.browser.maxthon = /(\d+\.\d)/.test(external.max_version) ? parseFloat(RegExp["\x241"]) : 0
	} catch(B) {
		baidu.browser.maxthon = 0
	}
	baidu.maxthon = baidu.browser.maxthon;
	baidu.isGecko = baidu.browser.isGecko = /gecko/i.test(A) && !/like gecko/i.test(A);
	baidu.isStrict = baidu.browser.isStrict = document.compatMode == "CSS1Compat";
	baidu.isWebkit = baidu.browser.isWebkit = /webkit/i.test(A)
})();
baidu.each = function(F, B) {
	if (typeof B != "function") {
		return F
	}
	if (F) {
		var A;
		if (F.length === undefined) {
			for (var C in F) {
				if (C in {}) {
					continue
				}
				A = B.call(F[C], F[C], C);
				if (A == "break") {
					break
				}
			}
		} else {
			for (var D = 0,
			E = F.length; D < E; D++) {
				A = B.call(F[D], F[D], D);
				if (A == "break") {
					break
				}
			}
		}
	}
	return F
};
baidu.dom = baidu.dom || {};
baidu.isElement = function(A) {
	if (A === undefined || A === null) {
		return false
	}
	return A && A.nodeName && A.nodeType == 1
};
baidu.isDocument = function(A) {
	if (A === undefined || A === null) {
		return false
	}
	return A && A.nodeType == 9
};
baidu.dom.getDocument = function(A) {
	if (baidu.isElement(A) || baidu.isDocument(A)) {
		return A.nodeType == 9 ? A: A.ownerDocument || A.document
	} else {
		throw new Error("[baidu.dom.getDocument] param must be Element or Document")
	}
};
baidu.isElement = function(A) {
	return A && A.nodeType == 1
};
baidu.dom.getPosition = function(D) {
	D = baidu.G(D);
	if (!baidu.isElement(D)) {
		throw new Error("[baidu.dom.getPosition] param must be Element")
	}
	var G = baidu.dom.getDocument(D);
	var F = baidu.isGecko > 0 && G.getBoxObjectFor && baidu.getCurrentStyle(D, "position") == "absolute" && (D.style.top === "" || D.style.left === "");
	var H = {
		left: 0,
		top: 0
	};
	var B = (baidu.ie && !baidu.isStrict) ? G.body: G.documentElement;
	if (D == B) {
		return H
	}
	var C = null;
	var E;
	if (D.getBoundingClientRect) {
		E = D.getBoundingClientRect();
		H.left = E.left + Math.max(G.documentElement.scrollLeft, G.body.scrollLeft);
		H.top = E.top + Math.max(G.documentElement.scrollTop, G.body.scrollTop);
		H.left -= G.documentElement.clientLeft;
		H.top -= G.documentElement.clientTop;
		alert(H.left);
		if (baidu.ie && !baidu.isStrict) {
			H.left -= 2;
			H.top -= 2
		}
	} else {
		if (G.getBoxObjectFor && !F) {
			E = G.getBoxObjectFor(D);
			var A = G.getBoxObjectFor(B);
			H.left = E.screenX - A.screenX;
			H.top = E.screenY - A.screenY
		} else {
			C = D;
			do {
				H.left += C.offsetLeft;
				H.top += C.offsetTop;
				if (baidu.isWebkit > 0 && baidu.getCurrentStyle(C, "position") == "fixed") {
					H.left += G.body.scrollLeft;
					H.top += G.body.scrollTop;
					break
				}
				C = C.offsetParent
			} while ( C && C != D );
			if (baidu.opera > 0 || (baidu.isWebkit > 0 && baidu.getCurrentStyle(D, "position") == "absolute")) {
				H.top -= G.body.offsetTop
			}
			C = D.offsetParent;
			while (C && C != G.body) {
				H.left -= C.scrollLeft;
				if (!baidu.opera || C.tagName != "TR") {
					H.top -= C.scrollTop
				}
				C = C.offsetParent
			}
		}
	}
	return H
};
baidu.encodeHTML = function(A) {
	return A.replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;").replace(/"/g, "&quot;")
};
baidu.ie = /msie (\d+\.\d)/i.test(navigator.userAgent) ? parseFloat(RegExp["\x241"]) : 0;
baidu.setAttr = function(C, A, D) {
	var B = {
		cellpadding: "cellPadding",
		cellspacing: "cellSpacing",
		colspan: "colSpan",
		rowspan: "rowSpan",
		valign: "vAlign",
		height: "height",
		width: "width",
		usemap: "useMap",
		frameborder: "frameBorder"
	};
	if (C = baidu.G(C)) {
		if (baidu.isString(A)) {
			if (A == "style") {
				C.style.cssText = D
			} else {
				if (A == "class") {
					C.className = D
				} else {
					if (A == "for") {
						C.htmlFor = D
					} else {
						if (A in B) {
							C.setAttribute(B[A], D)
						} else {
							C[A] = D
						}
					}
				}
			}
		} else {
			for (var E in A) {
				if (typeof A[E] != "function") {
					baidu.setAttr(C, E, A[E])
				}
			}
		}
	}
};
baidu.dom.setProperties = function(B, A) {
	B = baidu.G(B);
	baidu.each(A,
	function(D, C) {
		baidu.setAttr(B, C, D)
	})
};
baidu.dom.create = function(B, A) {
	B = String(B);
	if (B == "") {
		throw new Error("[baidu.dom.create] param tagName can not be empty")
	}
	if (baidu.ie && A && A.name) {
		B = "<" + B + ' name="' + baidu.encodeHTML(A.name) + '">'
	}
	var C = document.createElement(B);
	if (A) {
		baidu.dom.setProperties(C, A)
	}
	return C
};
baidu.suggestion = baidu.suggestion || {};
baidu.suggestion.create = function(B, A) {
	return new baidu.suggestion.Suggestion(B, A)
};
baidu.suggestion.Suggestion = function(L, N) {
	var H = this;
	H.options = {
		onpick: function() {},
		onconfirm: function() {},
		onhighlight: function() {},
		onhide: function() {},
		view: null,
		getData: false,
		prepend_html: "",
		append_html: "",
		class_prefix: "tangram_sug_"
	};
	baidu.extend(H.options, N);
	if (! (L = baidu.G(L))) {
		return null
	}
	if (L.id) {
		H.options._inputId = L.id
	} else {
		L.id = H.options._inputId = H.options.class_prefix + "ipt" + new Date().getTime()
	}
	var B = (document.compatMode == "BackCompat");
	function M(G) {
		return baidu.G(G)
	}
	function C(P, O, G) {
		if (baidu.ie) {
			P.attachEvent("on" + O, (function(Q) {
				return function() {
					G.call(Q)
				}
			})(P))
		} else {
			P.addEventListener(O, G, false)
		}
	}
	function A(G) {
		if (baidu.ie) {
			G.returnValue = false
		} else {
			G.preventDefault()
		}
	}
	function D(G, Q) {
		var P = document.styleSheets;
		if (!P || P.length <= 0) {
			var R = document.createElement("STYLE");
			R.type = "text/css";
			var O = document.getElementsByTagName("HEAD")[0];
			O.appendChild(R)
		}
		P = document.styleSheets;
		P = P[P.length - 1];
		if (baidu.ie) {
			P.addRule(G, Q)
		} else {
			P.insertRule(G + " { " + Q + " }", P.cssRules.length)
		}
	}
	H.dispose = function() {
		clearInterval(H.circleTimer);
		document.body.removeChild(M(H._sugEle))
	};
	var F = (function() {
		function G(P) {
			var R = this.__MSG_QS__;
			if (!R[P]) {
				R[P] = []
			}
			for (var Q = 1,
			T = arguments.length,
			S; Q < T; Q++) {
				R[P].push(arguments[Q])
			}
		}
		function O(R) {
			var Q = this.__MSG_QS__[R.type];
			if (Q == null) {
				return
			}
			for (var P = 0,
			S = Q.length; P < S; P++) {
				Q[P].receiveMessage(R)
			}
		}
		return {
			initialize: function(P) {
				P.__MSG_QS__ = {};
				P.addMessageReceiver = G;
				P.dispatchMessage = O;
				return P
			}
		}
	})();
	var K = (function() {
		var a = L;
		var P;
		H.circleTimer = 0;
		var S = 0;
		var G = "";
		var X = "";
		var U = "";
		var Z;
		var W = false;
		var Y = true;
		function R() {
			if (Y) {
				K.dispatchMessage({
					type: "start"
				});
				Y = false
			}
		}
		function T(c) {
			if (Y) {
				K.dispatchMessage({
					type: "start"
				});
				Y = false
			}
			c = c || window.event;
			if (c.keyCode == 9 || c.keyCode == 27) {
				K.dispatchMessage({
					type: "hide_div"
				})
			}
			if (c.keyCode == 13) {
				A(c);
				K.dispatchMessage({
					type: "key_enter"
				})
			}
			if (P.style.display != "none") {
				if (c.keyCode == 38) {
					A(c);
					K.dispatchMessage({
						type: "key_up",
						val: X
					})
				}
				if (c.keyCode == 40) {
					K.dispatchMessage({
						type: "key_down",
						val: X
					})
				}
			} else {
				if (c.keyCode == 38 || c.keyCode == 40) {
					K.dispatchMessage({
						type: "need_data",
						wd: a.value
					})
				}
			}
		}
		function O() {
			if (M(H.options._inputId) == null) {
				H.dispose()
			}
			var c = a.value;
			if (c == G && c != "" && c != U && c != Z) {
				if (S == 0) {
					S = setTimeout(function() {
						K.dispatchMessage({
							type: "need_data",
							wd: c
						})
					},
					100)
				}
			} else {
				clearTimeout(S);
				S = 0;
				if (c == "" && G != "") {
					K.dispatchMessage({
						type: "hide_div"
					})
				}
				G = c;
				if (c != Z) {
					X = c
				}
				if (U != a.value) {
					U = ""
				}
			}
		}
		function Q() {
			H.circleTimer = setInterval(O, 10)
		}
		function V() {
			if (W) {
				window.event.cancelBubble = true;
				window.event.returnValue = false;
				W = false
			}
		}
		function b(d) {
			a.blur();
			a.setAttribute("autocomplete", d);
			a.focus()
		}
		a.setAttribute("autocomplete", "off");
		C(a, "keydown", T);
		C(a, "mousedown", R);
		C(a, "beforedeactivate", V);
		return F.initialize({
			receiveMessage: function(c) {
				switch (c.type) {
				case "div_ready":
					P = c.sdiv;
					U = a.value;
					Q();
					break;
				case "clk_confirm":
					X = G = c.wd;
					H.options.onpick(c.oq, c.rsp, c.wd, c.html);
					c.oq = c.wd;
				case "ent_confirm":
					H.options.onconfirm(c.oq, c.rsp, c.wd, c.html);
					break;
				case "pick_word":
					Z = c.selected;
					break;
				case "mousedown_tr":
					W = true;
					break
				}
			}
		})
	})();
	var E = (function() {
		var Z;
		var g = L;
		var U;
		var c = -1;
		var l;
		var e;
		var S;
		var X;
		function T() {
			var m = U.rows;
			for (var n = 0; n < m.length; n++) {
				m[n].className = H.options.class_prefix + "ml"
			}
		}
		function b() {
			if (typeof(U) != "undefined" && U != null && Z.style.display != "none") {
				var m = U.rows;
				for (var n = 0; n < m.length; n++) {
					if (m[n].className == H.options.class_prefix + "mo") {
						return [n, l[n], e[n]]
					}
				}
			}
			return [ - 1, ""]
		}
		function Y() {
			if (Z.style.display == "none") {
				return null
			}
			X.style.display = "none";
			Z.style.display = "none";
			H.options.onhide()
		}
		H._hide = Y;
		function Q() {
			var m = b();
			H.options.onhighlight(g.value, m[0], m[1], m[2])
		}
		function G(m) {
			T();
			U.rows[m].className = H.options.class_prefix + "mo"
		}
		H._highlight = G;
		function P(m) {
			var n = b();
			H.options.onpick(m, n[0], n[1], n[2])
		}
		function R(m) {
			var n = l && typeof l[m] != "undefined" ? l[m] : m;
			E.dispatchMessage({
				type: "pick_word",
				selected: n
			});
			g.value = n
		}
		H._pick = R;
		function k() {
			T();
			this.className = H.options.class_prefix + "mo";
			Q()
		}
		function f(m) {
			E.dispatchMessage({
				type: "mousedown_tr"
			});
			if (!baidu.ie) {
				m.stopPropagation();
				m.preventDefault();
				return false
			}
		}
		function d(n) {
			var m = n;
			return function() {
				E.dispatchMessage({
					type: "clk_confirm",
					oq: g.value,
					wd: l[m],
					rsp: m,
					html: e[m]
				});
				Y()
			}
		}
		function i() {
			var m = [g.offsetWidth, g.offsetHeight];
			var n = baidu.dom.getPosition(g);
			return [(n.top + m[1] - 1), n.left, (m[0])]
		}
		function h() {
			var r = i();
			r = typeof H.options.view == "function" ? H.options.view(r) : r;
			Z.style.display = X.style.display = "block";
			X.style.top = r[0] + "px";
			X.style.left = r[1] + "px";
			X.style.width = r[2] + "px";
			var m = parseFloat(baidu.getCurrentStyle(Z, "marginTop")) || 0;
			var q = parseFloat(baidu.getCurrentStyle(Z, "marginLeft")) || 0;
			Z.style.top = (r[0] - m) + "px";
			Z.style.left = (r[1] - q) + "px";
			if (baidu.ie && B) {
				Z.style.width = r[2] + "px"
			} else {
				var p = parseFloat(baidu.getCurrentStyle(Z, "borderLeftWidth")) || 0;
				var o = parseFloat(baidu.getCurrentStyle(Z, "borderRightWidth")) || 0;
				var n = parseFloat(baidu.getCurrentStyle(Z, "marginRight")) || 0;
				Z.style.width = (r[2] - p - o - q - n) + "px"
			}
			X.style.height = Z.offsetHeight + "px"
		}
		function W() {
			U = baidu.dom.create("TABLE", {
				cellSpacing: 0,
				cellPadding: 2
			});
			var p = baidu.dom.create("tbody");
			U.appendChild(p);
			for (var o = 0,
			n = e.length; o < n; o++) {
				var q = p.insertRow( - 1);
				C(q, "mouseover", k);
				C(q, "mouseout", T);
				C(q, "mousedown", f);
				C(q, "click", d(o));
				var s = q.insertCell( - 1);
				s.innerHTML = e[o]
			}
			var r = baidu.dom.create("div", {
				"class": H.options.class_prefix + "pre"
			});
			r.innerHTML = H.options.prepend_html;
			var m = baidu.dom.create("div", {
				"class": H.options.class_prefix + "app"
			});
			m.innerHTML = H.options.append_html;
			Z.innerHTML = "";
			Z.appendChild(r);
			Z.appendChild(U);
			Z.appendChild(m);
			h()
		}
		function j() {
			var n = b();
			var m = n[0] == -1 ? S: n[1];
			E.dispatchMessage({
				type: "ent_confirm",
				oq: g.value,
				wd: m,
				rsp: n[0],
				html: n[2]
			})
		}
		function V(m) {
			c = b()[0];
			T();
			if (c == 0) {
				R(m);
				c--
			} else {
				if (c == -1) {
					c = l.length
				}
				c--;
				G(c);
				Q();
				var n = g.value;
				R(c);
				P(n)
			}
		}
		function a(m) {
			c = b()[0];
			T();
			if (c == l.length - 1) {
				R(m);
				c = -1
			} else {
				c++;
				G(c);
				Q();
				var n = g.value;
				R(c);
				P(n)
			}
		}
		function O(p) {
			l = [];
			e = [];
			for (var n = 0,
			m = p.length; n < m; n++) {
				if (p[n].input != undefined) {
					l[n] = p[n].input;
					e[n] = p[n].selection
				} else {
					e[n] = l[n] = p[n]
				}
			}
		}
		return F.initialize({
			receiveMessage: function(m) {
				switch (m.type) {
				case "div_ready":
					Z = m.sdiv;
					X = m.frm;
					break;
				case "give_data":
					S = m.data;
					O(m.word);
					if (l.length != 0) {
						W()
					} else {
						Y()
					}
					break;
				case "key_enter":
					j();
					break;
				case "key_up":
					V(m.val);
					break;
				case "key_down":
					a(m.val);
					break;
				case "clk_confirm":
					g.blur();
					R(m.rsp);
					g.focus();
				case "hide_div":
				case "mousedown_outside":
				case "window_blur":
				case "ent_confirm":
					Y();
					break;
				case "need_resize":
					h();
					break
				}
			}
		})
	})();
	var I = (function() {
		var G = {};
		function P(Q) {
			if (typeof G[Q] == "undefined") {
				H.options.getData && H.options.getData(Q)
			} else {
				I.dispatchMessage({
					type: "give_data",
					word: Q,
					data: G[Q]
				})
			}
		}
		H._giveData = function(R, Q) {
			H.dispatchMessage({
				type: "response_data",
				word: R,
				data: Q
			})
		};
		function O(R, Q) {
			G[R] = Q;
			I.dispatchMessage({
				type: "give_data",
				word: R,
				data: Q
			})
		}
		return F.initialize({
			receiveMessage: function(Q) {
				switch (Q.type) {
				case "response_data":
					O(Q.data);
					break;
				case "need_data":
					P(Q.wd);
					break
				}
			}
		})
	})();
	var J = (function() {
		var R = L;
		var U;
		var G;
		function S() {
			if (U.offsetWidth != 0 && R.offsetWidth != U.offsetWidth) {
				J.dispatchMessage({
					type: "need_resize"
				})
			}
		}
		function Q() {
			U = baidu.dom.create("DIV", {
				id: H.options.class_prefix + new Date().getTime(),
				"class": H.options.class_prefix + "wpr"
			});
			U.style.display = "none";
            document.body.appendChild(U);
			G = baidu.dom.create("IFRAME");
			baidu.addClass(G, H.options.class_prefix + "sd");
			G.style.display = "none";
			G.style.position = "absolute";
			G.style.borderWidth = "0px";
			document.body.insertBefore(G, U)
		}
		function O(V) {
			V = V || window.event;
			var W = V.target || V.srcElement;
			if (W == R) {
				return
			}
			while (W = W.parentNode) {
				if (W == U) {
					return
				}
			}
			J.dispatchMessage({
				type: "mousedown_outside"
			})
		}
		function T() {
			J.dispatchMessage({
				type: "window_blur"
			})
		}
		function P() {
			J.dispatchMessage({
				type: "div_ready",
				sdiv: U,
				frm: G
			});
			setInterval(S, 100);
			C(document, "mousedown", O);
			C(window, "blur", T);
			H._sugEle = U
		}
		return F.initialize({
			receiveMessage: function(V) {
				switch (V.type) {
				case "start":
					P();
					break;
				case "init":
					Q();
					break
				}
			}
		})
	})();
	F.initialize(H);
	K.addMessageReceiver("need_data", I);
	K.addMessageReceiver("close_div", E);
	K.addMessageReceiver("key_enter", E);
	K.addMessageReceiver("key_up", E);
	K.addMessageReceiver("key_down", E);
	K.addMessageReceiver("hide_div", E);
	K.addMessageReceiver("start", J);
	I.addMessageReceiver("give_data", E);
	H.addMessageReceiver("response_data", I);
	H.addMessageReceiver("init", J);
	E.addMessageReceiver("clk_confirm", E, K);
	E.addMessageReceiver("ent_confirm", K, E);
	E.addMessageReceiver("pick_word", K);
	E.addMessageReceiver("mousedown_tr", K);
	J.addMessageReceiver("mousedown_outside", E);
	J.addMessageReceiver("need_resize", E);
	J.addMessageReceiver("div_ready", K, E);
	J.addMessageReceiver("window_blur", E);
	H.dispatchMessage({
		type: "init"
	});
	return {
		getElement: function() {
			return H._sugEle
		},
		getData: H.options.getData,
		pick: H._pick,
		hide: H._hide,
		giveData: H._giveData,
		highlight: H._highlight
	}
};
