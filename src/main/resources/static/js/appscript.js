  const folderJson = { core : {
    data : [
       { "id" : "ROOT", "parent" : "#", "text" : "ROOT"},
       {"id":"child1", "parent": "ROOT", "text": "child1"}
       
      ]
    } };
   const folders = {
    "core" : {
        "data" : {"id":"ROOT","text":"ROOT","parent":"#","directory":false,"url":null,"state":null,"children":[{"id":"Opyland","text":"Opyland","parent":"ROOT","directory":true,"url":"/api/folders/Opyland","state":{"disabled":false},"children":null},{"id":"gauri","text":"gauri","parent":"ROOT","directory":true,"url":"/api/folders/gauri","state":{"disabled":false},"children":null},{"id":"Webcam","text":"Webcam","parent":"ROOT","directory":true,"url":"/api/folders/Webcam","state":{"disabled":false},"children":null},{"id":"Ishani","text":"Ishani","parent":"ROOT","directory":true,"url":"/api/folders/Ishani","state":{"disabled":false},"children":null},{"id":"Canon","text":"Canon","parent":"ROOT","directory":true,"url":"/api/folders/Canon","state":{"disabled":true},"children":null},{"id":"MomDadAnniverysary","text":"MomDadAnniverysary","parent":"ROOT","directory":true,"url":"/api/folders/MomDadAnniverysary","state":{"disabled":true},"children":null},{"id":"Poses","text":"Poses","parent":"ROOT","directory":true,"url":"/api/folders/Poses","state":{"disabled":false},"children":null},{"id":"Hande","text":"Hande","parent":"ROOT","directory":true,"url":"/api/folders/Hande","state":{"disabled":false},"children":null}]}
    }
   }
   const ajaxJson = { core: {
    multiple: false,
    data: {
        url: function(node) {
            console.log('url function called with node ' + JSON.stringify(node));
            return node.id === '#' ? '/api/folders' : '/api/folders/' + node.id;
        }
    }
   } };
/*
,
        data: function(node) {
            console.log('data function called with node ' + JSON.stringify(node));
            return { 'id': node.id, 'text': 'MYTEXT' };
        }
*/
    // Image array (need to fetch this from the REST API based on current folder)
    var imageArr = ["images/5bfad15a7fd24d448a48605baf52655a5bbe5a71.jpeg",
          "images/66cf5094908491e69d8187bcf934050a4800b37f.jpeg",
          "images/b7d624354d5fa22e38b0ab1f9b905fb08ccc6a05.jpeg"];

    let idx = 0;
    const imageEle = $("#myCarousel > .carousel-inner > .item > #ssimage");
    //Load the first image from the image array
    jQuery(imageEle).attr("src", imageArr[idx]);

    /*
      Function to handle prev and next image navigation in carousel
      where = 'prev' or 'next'
    */
    function cycle(where) {
        if(where === 'next') {
            idx++
        } else {
            idx--;
        }
        if(idx < 0) idx = imageArr.length-1;
        if(idx >= imageArr.length) idx = 0;
        jQuery(imageEle).attr("src", imageArr[idx]);
        //console.log(imageArr[idx]);
    }



    /*
      Jquery function called after document is completely loaded
    */
    $(document).ready(function() {
        console.log("document is ready");
        $('#jstree_navigation').on('changed.jstree', handleFolderNav).jstree(ajaxJson);

        $('#myCarousel').on('slide.bs.carousel', function () {
            console.log("The slide happened");

        });
    });

function getRootFolders() {
    return jQuery.get("/api/folders", function(data) {
        return data;
    });
}

function getChildFolders(id) {
    return jQuery.get("/api/folders/" + id , function(data) {
        return data;
    });
}

/*
  Handle the Folder navigation
*/
const handleFolderNav = function (e, data) {
            var i, j, r = [];
            for(i = 0, j = data.selected.length; i < j; i++) {
              r.push(data.instance.get_node(data.selected[i]).text);
            }
            $('#event_result').html('Selected: ' + r.join(', '));
            jQuery.get('/api/folders/' + r[0] + '/files', function(data) {
                for(var i=0; i < data.length; i++) {
                    data[i] = "/" + r[0] + "/" + data[i];
                }
                idx = 0;
                imageArr = data;
                jQuery(imageEle).attr("src", imageArr[idx]);
            });
};
