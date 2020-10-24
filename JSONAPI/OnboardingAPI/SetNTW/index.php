<?php

//Zet het NTW(antwoord) in de database


//kijkt of SID, VID, NTW aanwezig is zo niet geef een false resultaat
if(!isset($_GET['SID']) || !isset($_GET['VID']) || !isset($_GET['NTW'])){

    echo json_encode(array("result"=>false)); 

}else{

    //haal SID op en filter deze 
    $SID = htmlspecialchars(filter_var($_GET['SID'], FILTER_SANITIZE_STRING));
    //haal VID op en filter deze 
    $VID = htmlspecialchars(filter_var($_GET['VID'], FILTER_VALIDATE_INT));
    //haal NTW op en filter deze 
    $NTW = htmlspecialchars(filter_var($_GET['NTW'], FILTER_SANITIZE_STRING));


    
    //check of er nummers in staan 
    if(preg_match('~[0-9]+~', $SID) OR preg_match('~[0-9]+~', $NTW) OR empty($SID) OR empty($VID) OR empty($NTW)){

        echo json_encode(array("result"=>false)); 

    }else{

            //haalt de database File op
            require_once('../DB.php');

            //maakt een nieuwe connectie
            $database = new Database();
            $db = $database->getConnection();

            // de query  die geescaped wordt door sprintf
            $query = sprintf('UPDATE studentantwoord SET ntw = "%s" WHERE sID = "%s" AND vID =%u', $NTW, $SID, $VID);
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