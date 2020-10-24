<?php

//Start de db 


//kijkt of sid aanwezig is
if(!isset($_GET['SID'])){

    echo json_encode(array("result"=>false)); 

}else{


    //haal sid op en filter deze 
    $SID = htmlspecialchars(filter_var($_GET['SID'], FILTER_SANITIZE_STRING));

    //check of er nummers in staan en of hij leeg is
    if(preg_match('~[0-9]+~', $SID) OR empty($SID)){

        echo json_encode(array("result"=>false)); 

    }else{

            //haalt de json op en telt hoeveel vragen er instaan
            $json_tasks = file_get_contents("../JSONtest.txt");
            $task_array = json_decode($json_tasks,true);
            $vraag = $task_array['Count'];
            $vraagNUM = $vraag['count'];

            //haalt de database File op                  
            require_once('../DB.php');

            //maakt een nieuwe connectie
            $database = new Database();
            $db = $database->getConnection();

            // de query  die geescaped wordt door sprintf
            $query = sprintf('INSERT INTO studentminuutdatumvraag VALUES("%s", current_timestamp(), NULL, %u)', $SID, 1);
            // Query preparen, aka kijken of die uitgevoerd kan worden
            $stm1 = $db->prepare($query);


            // de query  die geescaped wordt door sprintf
            $query = sprintf('INSERT INTO studentfeedback VALUES("%s", NULL, NULL, NULL)', $SID);
            // Query preparen, aka kijken of die uitgevoerd kan worden
            $stm2 = $db->prepare($query);

            //vraag counter
            $vraagCOUNT = 0;

            //grebruikt de eerder opgehaalde aantal vragen om meerder rows in de database te maken
            //er wordt er altijd 1 extra gedaan voor promo 
            while($vraagCOUNT <= $vraagNUM){$vraagCOUNT++;
                
                // de query  die geescaped wordt door sprintf
                $query = sprintf('INSERT INTO studentantwoord VALUES("%s", %u, NULL)', $SID, $vraagCOUNT);
                // Query preparen, aka kijken of die uitgevoerd kan worden
                $stm3 = $db->prepare($query);
                $stm3->execute();
            }
            
            
            if(!$stm1->execute() || !$stm2->execute())
            {
   
                echo json_encode(array("result"=>false)); 
            }else{
   
                 echo json_encode(array("result"=>true));
           }  
    }
}














































?>