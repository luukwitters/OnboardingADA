<?php

//update het VID(Vraag id) in de database


//kijkt of SID, VID aanwezig is
if(!isset($_GET['SID']) || !isset($_GET['VID'])){

    echo json_encode(array("result"=>false)); 

}else{

    //haal SID op en filter deze 
    $SID = htmlspecialchars(filter_var($_GET['SID'], FILTER_SANITIZE_STRING));
    //haal VID op en filter deze 
    $VID = htmlspecialchars(filter_var($_GET['VID'], FILTER_VALIDATE_INT));


    //check of er nummers in staan 
    if(preg_match('~[0-9]+~', $SID) OR empty($SID) OR empty($VID)   ){

        echo json_encode(array("result"=>false)); 

    }else{

            //haalt de database File op
            require_once('../DB.php');

            //maakt een nieuwe connectie
            $database = new Database();
            $db = $database->getConnection();

            // de query  die geescaped wordt door sprintf
            $query = sprintf('UPDATE studentminuutdatumvraag SET vID = %u WHERE sID = "%s"', $VID, $SID);
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