<?php

//haalt het VID(vraag id) op


//kijkt of sid aanwezig is zo niet geef een false resultaat
if(!isset($_GET['SID'])){

    echo json_encode(array("result"=>false));

}else{

    //haal sid op en filter deze 
    $SID = htmlspecialchars(filter_var($_GET['SID'], FILTER_SANITIZE_STRING));

    //check of er nummers in staan 
    if(preg_match('~[0-9]+~', $SID) OR empty($SID)){

        echo json_encode(array("result"=>false));

    }else{

            //haalt de database File op
            require_once('../DB.php');

            //maakt een nieuwe connectie 
            $database = new Database();
            $db = $database->getConnection();

            // de query  die geescaped wordt door sprintf
            $query = sprintf('SELECT vID FROM studentminuutdatumvraag WHERE sID = "%s"', $SID);
            // Query preparen, aka kijken of die uitgevoerd kan worden
            $stm = $db->prepare($query);
            
            if($stm->execute()) {

                $result = $stm->fetchColumn();
                // Resultaat ophalen
                //$result = $stm->fetchAll(PDO::FETCH_OBJ);
                
                    // JSON teruggeven
                    echo json_encode(array("result"=>$result));
            }    
    }
}














































?>