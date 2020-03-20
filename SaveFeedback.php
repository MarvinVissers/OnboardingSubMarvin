<?php
    /**
    * Opslaan feedback
    */

    // Kijken of er een studentnummer is meegegeven
    if(!isset($_GET['score']) && !isset($_GET['opmerking'])){
        $error = "Er is geen score en geen opmerking meegegeven";
    }else {
        // Studentnummer in URL omzetten naar variabele
        $score = htmlspecialchars($_GET['score']);
        $opmerking = htmlspecialchars($_GET['opmerking']);

        // Database connection class aanspreken
        require_once('database.class.php');

        // Variabele aanmaken voor connectie
        $database = new Database();
        $db = $database->getConnection();

        // Query maken
        // sprintf is mijn manier van querys escapen
        $query = sprintf('INSERT INTO feedback VALUES(NULL, %s, "%s")', $score, $opmerking);
        // Query preparen, aka kijken of die uitgevoerd kan worden
        $stm = $db->prepare($query);
        // Als de query uitgevoerd wordt
        if($stm->execute()) {
            // Resultaat ophalen
            $result = 1;
            
            // JSON teruggeven
            echo json_encode($result);
        }   
    }
?>
