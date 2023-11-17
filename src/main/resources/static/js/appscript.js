
    // Image array (need to fetch this from the REST API based on current folder)
    var imageArr = [];
    let idx = 0;
    const imageEle = $("#myCarousel > .carousel-inner > .item > #ssimage");
    //Load the first image from the image array

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
    }

    function getFiles(path) {
            jQuery.get('/api/files/' + path , function(data) {
                for(var i=0; i < data.length; i++) {
                    data[i] = ( (path !== null &&  path.length > 0) ?
                        "/" + path : "") + "/" + encodeURIComponent(data[i]) ;
                }
                idx = 0;
                imageArr = data;
                jQuery(imageEle).attr("src", imageArr[idx]);
            });
    }

    /*
      Handle the Folder navigation
    */
    const handleFolderNav = function (e, data) {
                var i, j, r = [];
                console.log("Selected data: " + data.selected[0]);
                var selected = data.instance.get_node(data.selected[0]).original;
                console.log("Selected: " + JSON.stringify(selected));
                $('#event_result').html('Showing Folder: ' + selected.relativePath);
                getFiles(selected.relativePath);
    };

    var menuItems = [];

    function makeObject(node) {
        var menuhtml = '<li id="menu_' + node.text + '"><a href="#" data-toggle="collapse" data-target="#navbar" onclick=getFiles("' + node.relativePath + '")>' + node.text +  '</a></li>';
        return {
            object: node,
            text: node.text,
            modified: false,
            children: [],
            html:  menuhtml
        }
    }
    function buildMenu() {
        // TODO = hide menu after click
        jQuery.get('api/folders', function(data) {
            for(var i=0; i < data.length; i++) {
                var curr = data[i];
                var menuItem = null;
                //console.log("Parent for " + curr.text + " is " + curr.parent);
                if(curr.parent === 'ROOT') {
                    menuItem = makeObject(curr);
                    menuItems.push(menuItem);
                    $('#dropdown_root').append(menuItem.html);
                    console.log("Add top level menu " + menuItem.text);
                } else {
                    var parentMenu = menuItems.find((e) => e.text === curr.parent);
                    if(parentMenu != null) {
                        if(!parentMenu.modified) {
                            var htmlele = jQuery('#menu_' + parentMenu.text);
                            htmlele.addClass('dropdown-submenu');
                            htmlele.remove
                            htmlele.append('<ul class="dropdown-menu" id="dropdown_' + parentMenu.text + '">' +
                                '<li id="menu_' + curr.text + '"><a href="#" data-toggle="collapse" data-target="#navbar" onclick=getFiles("' + curr.relativePath + '")>' + curr.text +  '</a></li></ul>');
                            parentMenu.modified = true;
                            console.log("Add first child menu " + curr.text + "  to " + parentMenu.text);
                        } else {
                           var htmlele = jQuery('#dropdown_' + parentMenu.text);
                           htmlele.append('<li id="menu_' + curr.text + '"><a href="#" data-toggle="collapse" data-target="#navbar" onclick=getFiles("' + curr.relativePath + '")>' + curr.text +  '</a></li>');
                            console.log("Add another child menu" + curr.text + " to " + parentMenu.text);
                        }
                        parentMenu.children.push(makeObject(curr));
                    }
                }
                //$('#dropdown_root').append('<li><a href="#" data-toggle="collapse" data-target="#navbar" onclick=getFiles("' + data[i].relativePath + '")>' + data[i].text +  '</a></li>');
                //if(curr.html !== undefined) {
                //    $('#dropdown_root').append(curr.html);
                //}
            }
        });
        getFiles("");
    }

    // For JS Tree (which we are not using for now)
    const ajaxJson = { core: {
    multiple: false,
    data: {
        url: function(node) {
            console.log('url function called with node ' + JSON.stringify(node));
            return node.id === '#' ? '/api/folders' : node.url;
        }
    }
    } };

    /*
      Jquery function called after document is completely loaded
    */
    $(document).ready(function() {
        jQuery(imageEle).attr("src", 'images/loader.gif');
        console.log("document is ready");
        //We are not using JS Tree navigation, but we can use it later
       // $('#jstree_navigation').on('changed.jstree', handleFolderNav).jstree(ajaxJson);

        $('#myCarousel').on('slide.bs.carousel', function () {
            console.log("The slide happened");

        });
        buildMenu();
    });


// UN-USED
/*

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
   */
