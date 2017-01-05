<script type="text/javascript" src="${basePath}js/ext/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="${basePath}js/ext/ext-all.js"></script>
<script type="text/javascript" src="${basePath}js/ext/ux/TableGrid.js"></script>
<link rel="stylesheet" type="text/css" href="${basePath}js/ext/resources/css/ext-all.css" />
<script>
     Ext.onReady(function() {
         var grid = new Ext.ux.grid.TableGrid("dataTable", {
          stripeRows: false // stripe alternate rows
      }); 
      grid.render();
     });
 </script>