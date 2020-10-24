<?php

//zet het FB(feedback) in de database


//kijkt of SID ,FCODE ,FANT aanwezig is zo niet geef een false resultaat
// In plaats van FCODE mark1, mark2 etc.
//if(!isset($_GET['SID']) || !isset($_GET['FCODE']) || !isset($_GET['FANT'])){
    if(!isset($_GET['SID']) || !isset($_GET['mrk1']) || !isset($_GET['mrk2']) || !isset($_GET['fdb'])){

    echo json_encode(array("result"=>false)); 

    echo "iets anders";

}else{
    echo "We hebben iets <br>";
    //haal SID op en filter deze 
    $SID = htmlspecialchars(filter_var($_GET['SID'], FILTER_SANITIZE_STRING));
    //haal FCODE op en filter deze
    //$FCODE = htmlspecialchars(filter_var($_GET['FCODE'], FILTER_SANITIZE_STRING));
    //haal FANT op en filter deze
    //$FANT = htmlspecialchars(filter_var($_GET['FANT'], FILTER_SANITIZE_STRING));
    // Haal de mrk1 op en filter deze
    $mrk1 = htmlspecialchars(filter_var($_GET['mrk1'], FILTER_SANITIZE_STRING));
    // Haal de mrk2 op en filter deze
    $mrk2 = htmlspecialchars(filter_var($_GET['mrk2'], FILTER_SANITIZE_STRING));
    // Haal de fdb op en filter deze
    $fdb = htmlspecialchars(filter_var($_GET['fdb'], FILTER_SANITIZE_STRING));

    echo "dan je had gedacht <br>";
    
    //check of er nummers in staan en of er een leeg is
    //if(preg_match('~[0-9]+~', $SID) OR empty($SID) OR empty($FCODE)){
    if(preg_match('~[0-9]+~', $SID) || empty($SID) || empty($mrk1) || empty($mrk2)){

        echo json_encode(array("result"=>false)); 

        echo "zitten smerige nummers in";

    }else{

            //haalt de database File op
            require_once('../DB.php');

            //maakt een nieuwe connectie 
            $database = new Database();
            $db = $database->getConnection();

            echo "maar toch niet helemaal <br>";
            // de query  die geescaped wordt door sprintf
            //$query = sprintf('UPDATE studentfeedback SET '.$FCODE.' = "%s" WHERE sID = "%s"', $FANT, $SID);
            //$query = sprinf('UPDATE studentfeedback SET mrk1 = "%s", mrk2 = "%s", fdb = "%s" WHERE sID = "%s"', $mrk1, $mrk2, $fdb, $SID);
            $query = 'UPDATE studentfeedback SET mrk1 = "'.$mrk1.'", mrk2 = "'.$mrk2.'", fdb = "'.$fdb.'" WHERE sID = "'.$SID.'"';
            // Query preparen, aka kijken of die uitgevoerd kan worden
            echo $query . "<br>";
            $stm = $db->prepare($query);
            
            echo "maar toch wel <br>";
            //voert de query uit en kijkt of er een resultaat is 
            if(!$stm->execute())
            {
                echo "kut query <br>";

                echo json_encode(array("result"=>false)); 
            }else{

                echo "iets minder kutte phpmyadmin <br>";
                echo json_encode(array("result"=>true)); 
        }
    }
}














































?>