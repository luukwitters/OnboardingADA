<?php

//zet het FB(feedback) in de database


//kijkt of SID ,FCODE ,FANT aanwezig is zo niet geef een false resultaat
if(!isset($_GET['SID']) || !isset($_GET['FCODE']) || !isset($_GET['FANT'])){

    echo json_encode(array("result"=>false)); 

}else{

    //haal SID op en filter deze 
    $SID = htmlspecialchars(filter_var($_GET['SID'], FILTER_SANITIZE_STRING));
    //haal FCODE op en filter deze
    $FCODE = htmlspecialchars(filter_var($_GET['FCODE'], FILTER_SANITIZE_STRING));
    //haal FANT op en filter deze
    $FANT = htmlspecialchars(filter_var($_GET['FANT'], FILTER_SANITIZE_STRING));

    
    //check of er nummers in staan en of er een leeg is
    if(preg_match('~[0-9]+~', $SID) OR empty($SID) OR empty($FCODE)){

        echo json_encode(array("result"=>false)); 

    }else{

            //haalt de database File op
            require_once('../DB.php');

            //maakt een nieuwe connectie 
            $database = new Database();
            $db = $database->getConnection();

            // de query  die geescaped wordt door sprintf
            $query = sprintf('UPDATE studentfeedback SET '.$FCODE.' = "%s" WHERE sID = "%s"', $FANT, $SID);
            // Query preparen, aka kijken of die uitgevoerd kan worden
            $stm = $db->prepare($query);
            
            //voert de query uit en kijkt of er een resultaat is 
            if(!$stm->execute())
            {

                echo json_encode(array("result"=>false)); 
            }else{

                echo json_encode(array("result"=>true)); 
        }
    }
}














































?>