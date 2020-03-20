<?php
    /**
    * Krijgen van een student
    */

    // Kijken of er een studentnummer is meegegeven
    if(!isset($_GET['studentnummer'])){
        $error = "Er is geen studentnummer meegegeven";
    }else {

        // Studentnummer in URL omzetten naar variabele
        $studentnummer = htmlspecialchars($_GET['studentnummer']);

        // Database connection class aanspreken
        require_once('database.class.php');

        // Variabele aanmaken voor connectie
        $database = new Database();
        $db = $database->getConnection();

        // Query maken
        // sprintf is mijn manier van querys escapen
        $query = sprintf('SELECT st.studentnummer, st.student_voornaam, st.student_achternaam, op.opleiding_naam, st.start_datum, st.gegevens_correct
                          FROM student st
                          INNER JOIN opleiding op on st.opleiding_id = op.opleiding_id 
                          WHERE studentnummer = %s', $studentnummer);
        // Query preparen, aka kijken of die uitgevoerd kan worden
        $stm = $db->prepare($query);
        // Als de query uitgevoerd wordt
        if($stm->execute()) {
            // Resultaat ophalen
            $result = $stm->fetchAll(PDO::FETCH_OBJ);
            
            // JSON teruggeven
            echo json_encode(array("item"=>$result));
        }   
    }
?>
