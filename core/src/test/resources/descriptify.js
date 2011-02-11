/**
 * JQuery Descriptify plugin
 * Turn an element with descriptive text into an icon which
 * shows the descriptive text when it is clicked
 *
 * @require jQuery 1.2.6
 * @require shadowify for drop shadow which requires jQuery 1.3.2
 *
 * @author Amjad Mohamed
 */

(function ($) {
	$.fn.descriptify = function() {
		this.each(function() {
			$this = $(this);
			
			var text = $('<span class="descriptified" />');
			text.text($this.text());
			
			var icon = $('<span class="descriptify-icon" />');
			icon.bind('mouseover', function(e) {
				if (text.css('display') == 'none') {
					text.css('top', e.pageY+5);
					text.css('left', e.pageX+5);
					text.trigger('fadeIn');
					text.fadeIn('fast');
				}
			})
			.bind('mouseout', function() {
				if (text.css('display') == 'block') {
					text.trigger('fadeOut');
					text.fadeOut('fast');
				}
			});
			
			$this.html('');
			$this.append(icon);
			$('body').append(text);
			text.css('display', 'none');
			
			text.shadowify();
		});
		
		return this;
	};
})(jQuery);