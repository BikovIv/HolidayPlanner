function bindEvents(){
    $('#register').on('click', registerClick);
}

function registerClick(){
	$('#register-modal').modal('show');
}

function renderUserActivityInsertForm(){
  		$('#activity-form-modal').modal('show');
  	}
