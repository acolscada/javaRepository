/**
 <b>Ace demo functions</b>. Calls sidebar functions, demo ajax, some fixes, etc
*/

//document ready function
jQuery(function($) {
  try {
	ace.demo.init();
  } catch(e) {}
});

(function($ , undefined) {

	ace.demo = {
		functions: {},
		
		init: function(initAnyway) {
			//initAnyway used to make sure the call is from our RequireJS app and not a document ready event!
			var initAnyway = !!initAnyway && true;
			if(typeof requirejs !== "undefined" && !initAnyway) return;
			
			for(var func in ace.demo.functions) if(ace.demo.functions.hasOwnProperty(func)) {
				ace.demo.functions[func]();
			}
		}
	}


	ace.demo.functions.basics = function() {
		// for android and ios we don't use "top:auto" when breadcrumbs is fixed
		if(ace.vars['non_auto_fixed']) {
			$('body').addClass('mob-safari');
		}

		ace.vars['transition'] = ace.vars['animation'] || !!$.support.transition;
	}
	//Load content via ajax
	ace.demo.functions.enableDemoAjax = function() {
		if(!$.fn.ace_ajax) return;
		if(window.Pace) {
			window.paceOptions = {
				ajax: true,
				document: true,
				eventLag: false // disabled
				//elements: {selectors: ['.page-content-area']}
			}
		}

		var demo_ajax_options = {
			 'close_active': true,
			 close_mobile_menu: '#sidebar',
			 close_dropdowns: true,
			 'default_url': 'page/index',//default hash
			 'content_url': function(hash) {
				//***NOTE***
				//this is for Ace demo only, you should change it to return a valid URL
				//please refer to documentation for more info

				if( !hash.match(/^page\//) ) return false;
				var path = document.location.pathname;

				//for example in Ace HTML demo version we convert /ajax/index.html#page/gallery to > /ajax/content/gallery.html and load it
				if(path.match(/index\.html/))
					return path.replace(/index\.html/, 'content/'+hash.replace(/^page\//, '')+'.html') ;

				//for example in Ace PHP demo version we convert "ajax.php#page/dashboard" to "ajax.php?page=dashboard" and load it
				return path + "?" + hash.replace(/\//, "=");
			  }			  
		}
		   
		//for IE9 and below we exclude PACE loader (using conditional IE comments)
		//for other browsers we use the following extra ajax loader options
		//if(window.Pace) {
			demo_ajax_options['loading_overlay'] = 'body';//the opaque overlay is applied to 'body'
		//}

		//initiate ajax loading on this element( which is .page-content-area[data-ajax-content=true] in Ace's demo)
		$('[data-ajax-content=true]').ace_ajax(demo_ajax_options)

		//if general error happens and ajax is working, let's stop loading icon & PACE
		$(window).on('error.ace_ajax', function() {
			$('[data-ajax-content=true]').each(function() {
				var $this = $(this);
				if( $this.ace_ajax('working') ) {
					if(window.Pace && Pace.running) Pace.stop();
					$this.ace_ajax('stopLoading', true);
				}
			})
		})
	}

	

})(jQuery);