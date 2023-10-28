
 function uploadFiles() {
    var form = document.getElementById('uploadForm');
    var formData = new FormData(form);

    fetch('/upload', {
        method: 'POST',
        body: formData,
    })
    .then(response => {   
        if (response.ok) {
            return response.text();
        } else {
            throw new Error('File upload failed');
        }
    })
    .then(data => {
        Swal.fire({
            icon: 'success',
            title: 'Files Uploaded Successfully',
            text: data,  // You can customize the message
        });
    })
    .catch(error => {
        console.error('Error:', error);
        Swal.fire({
            icon: 'error',
            title: 'File Upload Failed',
            text: 'An error occurred while uploading files. Please try again.',
        });
    });
}

        
     
        
    function showLivePage(){
    	  alert("working this function");
    	  
    	   
    	  
      }  
        
        
        
        
      
 
 