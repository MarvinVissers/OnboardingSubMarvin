<?php
    /**
    * Updaten van gegevens_correct student
    */

    // Kijken of er een studentnummer is meegegeven
    if(!isset($_GET['studentnummer']) || !isset($_GET['gegevens-correct'])){
        $error = "Er is geen studentnummer of gegevens correct meegegeven";
    }else {

        // Studentnummer in URL omzetten naar variabele
        $studentnummer = htmlspecialchars($_GET['studentnummer']);
        $gegevensCorrect = htmlspecialchars($_GET['gegevens-correct']);

        // Database connection class aanspreken
        require_once('database.class.php');

        // Variabele aanmaken voor connectie
        $database = new Database();
        $db = $database->getConnection();

        // Query maken
        // sprintf is mijn manier van querys escapen
        $query = sprintf('UPDATE student SET gegevens_correct = %s WHERE studentnummer = %s', $gegevensCorrect, $studentnummer);
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
