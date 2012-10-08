
<%@page import="projet.*"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    Projet projet = (Projet) request.getAttribute("projet");
%>
<html>
    <head>


        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>visionProjet</title>
        <link rel="stylesheet" href="http://code.jquery.com/ui/1.8.24/themes/base/jquery-ui.css" type="text/css" media="all" /> 
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.0/jquery.min.js" type="text/javascript"></script> 
        <script src="http://code.jquery.com/ui/1.8.24/jquery-ui.min.js" type="text/javascript"></script> 
        <script src="http://jquery-ui.googlecode.com/svn/tags/latest/external/jquery.bgiframe-2.1.2.js" type="text/javascript"></script> 
        <script src="http://jquery-ui.googlecode.com/svn/tags/latest/ui/minified/i18n/jquery-ui-i18n.min.js" type="text/javascript"></script> 
        <script>
            jQuery(document).ready(function(){
                $('.afficher').mouseenter(function () {
              
                    $(this).append($("#description"));
                    var pos = $(this).position();              

                    $("#description").css({
                        position: "absolute",
                        top: (pos.top+20) + "px",
                        left: (pos.left+20 ) + "px"
                    });
                    $( "#description" ).show( "blind", {}, 500 );
                    $( "#description" ).text($(this).find('span').text());
 
 

                    //$( "#description" ).position(p);

                });
                
                $('.afficher').mouseout(function () {
                    $( "#description" ).hide();
                });         
            });
 
 
             jQuery(document).ready(function(){
                $('.estUnMembre').click(function () {
                        document.location.href="/Gestion-taches-fun-avec-J2EE/Membre?id="+this.id;
                });         
            });
 
            jQuery(document).ready(function(){
                $(".sourisTrack").click(function(e){
                    $( "#dialog-modal" ).dialog( "open" );
                    var x = e.pageX - this.offsetLeft;
                    var y = e.pageY - this.offsetTop;
                    $( "#dialog-modal" ).dialog( "option", "position", [x,y] );
                    $("#location").val( this.id);
                    
                }); 
            });
            jQuery(document).ready(function(){
                $(".sourisTrackRajouterTache").click(function(e){
                    $( "#dialog-modal2" ).dialog( "open" );
                    var x = e.pageX - this.offsetLeft;
                    var y = e.pageY - this.offsetTop;
                    $( "#dialog-modal2" ).dialog( "option", "position", [x,y] );                    
                }); 
            } );
            
            $(function() {
                $( "#dialog-modal" ).dialog( "close" );
                $( "#dialog-modal" ).dialog({
                    autoOpen: false,
                    height: 110,
                    width: 350,
                    modal: false
                });
              
                var availableTags = [
            <%
                for (int i = 0; i < projet.getToutLesMembres().size(); i++) {
                    out.print("\"" + projet.getToutLesMembres().get(i).getNom() + "  " + projet.getToutLesMembres().get(i).getPrenom() + " - " + projet.getToutLesMembres().get(i).getId() + "\"" + ", ");
                }
                out.print("\" \"");
            %>
                    ];
                    $( "#ajout" ).autocomplete({
                        source: availableTags
                    });        

                });
                
                $(function() {
                    $( "#depart" ).datepicker( $.datepicker.regional[ "fr" ] );
                    $( "#fin" ).datepicker( $.datepicker.regional[ "fr" ] );

                    $( "#dialog-modal2" ).dialog( "close" );
                    $( "#dialog-modal2" ).dialog({
                        autoOpen: false,
                        height: 350,
                        width: 450,
                        modal: false
                    });
                });

        </script>

    </head>
    <body> 
        <%
            out.println((String) request.getAttribute("messageAlerte"));
        %>

        <table> <tr>
                <td width="300px"> <h1>${ projet.getName() }   </h1> </td>   <td>   <form method="post" action="serveur"><input id="save" name="save" type="submit" value="Sauvegarder en local."></form>  </td> 
        </tr></tr></table>
        <text size="10px">==>  ${ projet.etatProjet() } </text>
    </h1>

    <form method="post" action="serveur">

        <TABLE BORDER="2" style=" border-spacing :20px;">
            <tr><td>
                    <TABLE BORDER="1">

                        <%
                            for (int i = 0; i < projet.getNbTaches(); i++) {%>
                        <TR>
                            <TD width="400px"> 
                                <span class="afficher">
                                    <%out.print(projet.getTache(i).getNom());%> 
                                    <span style="display:none;"> 
                                        <%out.print(projet.getTache(i).getDescription());%> 
                                    </span>  
                                </span>
                                <table> <tr><td><%out.print(projet.getTache(i).getDepart());%></td><td width="300px"><div style="height:10px; width: 80% "  id="progressbar<%out.print(i);%>"></div>
                                        </td><td><%out.print(projet.getTache(i).getDuree());%></td></tr></table>
                                <script>
                                    $(function() {
                                         
                                        $( "#progressbar<%out.print(i);%>" ).progressbar({
                                            value: <% out.print(projet.getTache(i).percent());%>
                                        });
                                    });
                                                                    
                                </script>


                                </br>
                                <TABLE style="margin-left:50px">
                                    <%
                                        for (int j = 0; j < projet.getTache(i).getNbMembres(); j++) {
                                    %>
                                    <tr>
                                        <td>
                                            <span class="estUnMembre" id="<% out.print(projet.getTache(i).getMembre(j).getId());%>"> 
                                                <%out.print(projet.getTache(i).getMembre(j));%>
                                            </span>
                                        </td>
                                        <TD>  <input style="background:none;" id="r_<%out.print(i);%>_<%out.print(j);%>" name="r_<%out.print(i);%>_<%out.print(j);%>" type="submit" value="Retirer membre"> </TD> 
                                    </tr>
                                    <%}%>
                                    <TD>  <input class="sourisTrack" id="a_<%out.print(i);%>" name="a_<%out.print(i);%>" type="button" value="Ajouter une personne"> </TD> 

                                </TABLE>
                            </TD> 
                            <TD>  <input id="e_<%out.print(i);%>" name="e_<%out.print(i);%>" type="submit" value="Effacer"> </TD> 

                        </TR>
                        <%}%>

                        <tr> <td> <input class="sourisTrackRajouterTache" type="button" value="Rajouter une tache"> </td></tr> 

                    </TABLE>
                </td>               
                <td>   
                    <TABLE BORDER="0">
                        <%
                            for (int i = 0; i < projet.getToutLesMembres().size(); i++) {%>
                        <TR>
                            <TD> 
                                <%out.print(projet.getToutLesMembres().get(i).getId());%>
                            </TD> 
                            <TD> 
                                <span class="estUnMembre" id="<% out.print(projet.getToutLesMembres().get(i).getId());%>"> 
                                <%out.print(projet.getToutLesMembres().get(i).getNom());%>  
                                <%out.print(projet.getToutLesMembres().get(i).getPrenom());%>
                                </span>
                            </TD> 
                        </TR>
                        <%}%>

                    </TABLE>
                </td> 
        </TABLE>

    </form >


    <div  id="dialog-modal"  title="Personne à ajouter" style="background-color:lightblue;">
        <form method="post" action="serveur">
            <input type="text"  name="ajout" id="ajout"/>
            <input hidden="1" type="text" value="default" name="location" id="location"/>
            <input type="submit" value="Valider"> 
        </form >
    </div>
    <div  id="dialog-modal2"  title="Rajouter une tache" style="background-color:lightblue;">
        <form method="post" action="serveur">
            <TABLE BORDER=0>
                <TR><TD>Nom</TD><TD> <INPUT type=text id="nomNew" name="nomNew"></TD></TR>
                <TR> <TD>Description</TD><TD> <TEXTAREA rows="3" name="description" id="description"></TEXTAREA></TD> </TR>
                <TR><TD> Date commencement</TD><TD><input type="text"  name="depart" id="depart"/>     </TD>
                </TR><TR><TD> Date fin</TD><TD><input type="text"  name="fin" id="fin"/>                        </TD></TR>
            </TABLE>
            <input type="submit" value="Valider"> 
        </form >
    </div>

    <div id="description" class="ui-widget-content ui-corner-all" style="display: none;    position:absolute; ">
        sdsdfsdfsdf
    </div>

</body>
</html>
