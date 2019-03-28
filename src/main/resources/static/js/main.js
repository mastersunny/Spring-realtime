//function loadComments () {
//
//    this.source = null;
//
//    this.start = function () {
//
//        var commentTable = document.getElementById("comments");
//
//        this.source = new EventSource("/api/v1/blog");
//
//        this.source.addEventListener("message", function (event) {
//
//            // These events are JSON, so parsing and DOM fiddling are needed
//            var comment = JSON.parse(event.data);
//
//            var row = commentTable.getElementsByTagName("tbody")[0].insertRow(0);
//            var cell0 = row.insertCell(0);
//            var cell1 = row.insertCell(1);
//            var cell2 = row.insertCell(2);
//
//            cell0.className = "author-style";
//            cell0.innerHTML = comment.id;
//
//            cell1.className = "text";
//            cell1.innerHTML = comment.content;
//
//            cell2.className = "date";
//            cell2.innerHTML = comment.author;
//
//        });
//
//        this.source.onerror = function () {
//            this.close();
//        };
//
//    };
//
//    this.stop = function() {
//        this.source.close();
//    }
//
//}
//
//comment = new loadComments();
//
//function myFunction() {
//	comment.start();
//}
//
///*
// * Register callbacks for starting and stopping the SSE controller.
// */
////window.onload = function() {
////    comment.start();
////};
////window.onbeforeunload = function() {
////    comment.stop();
////}