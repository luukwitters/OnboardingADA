<?php

//zet de EDT(Eind datum) in de database


//kijkt of sid aanwezig is zo niet geef een false resultaat
if(!isset($_GET['SID'])){

    echo json_encode(array("result"=>false)); 

}else{

    //haal sid op en filter deze 
    $SID = htmlspecialchars(filter_var($_GET['SID'], FILTER_SANITIZE_STRING));

    //haalt de tijd op in tijdzone amsterdam
    date_default_timezone_set('Europe/Amsterdam');
    $EDT = date('Y/m/d h:i:s', time());

    //check of er nummers in staan of dat er lege strings in staan 
    if(preg_match('~[0-9]+~', $SID) OR empty($SID) OR empty($EDT)){

        echo json_encode(array("result"=>false));

    }else{

            //haalt de database File op
            require_once('../DB.php');

            //maakt een nieuwe connectie 
            $database = new Database();
            $db = $database->getConnection();

            // de query  die geescaped wordt door sprintf
            $query = sprintf('UPDATE studentminuutdatumvraag SET eDT = "%s" WHERE sID = "%s"', $EDT, $SID);
            // Query preparen, aka kijken of die uitgevoerd kan worden
            $stm = $db->prepare($query);
            
            //kijkt of het uitgevoerd is en geeft dan een resultaat
            if(!$stm->execute())
            {

                echo json_encode(array("result"=>false)); 
            }else{

                echo json_encode(array("result"=>true)); 
        }   
    }
}














































?>