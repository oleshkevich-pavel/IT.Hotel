$(document).ready(function() {
	$('select').formSelect();
	
	$('.datepicker').datepicker({
		selectMonths : true, // Creates a dropdown to control month
		selectYears : 100, // Creates a dropdown of 15 years to control year,
		today : 'Today',
		clear : 'Clear',
		close : 'Ok',
		closeOnSelect : false,
	// Close upon selecting a date,
		format : 'yyyy-mm-dd'
	});
	 $('.timepicker').timepicker({
	 });
	 $('.parallax').parallax();
	 $('.tabs').tabs({
			 swipeable: true});
	 $('.fixed-action-btn').floatingActionButton();
	 $('.slider').slider();
	 $('.materialboxed').materialbox();
	 $('.carousel').carousel();
	 $('.carousel.carousel-slider').carousel({
		    fullWidth: true
		  });
	 $('.tooltipped').tooltip();
	 $('.modal').modal();
	 $('.collapsible').collapsible();
});