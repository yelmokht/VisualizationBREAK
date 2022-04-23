package com.app.visualizationbreak.Model.gameorganisator;
// version Sami



import com.app.visualizationbreak.Model.factories.FarmerFactory;
import com.app.visualizationbreak.Model.factories.TowerFactory;
import com.app.visualizationbreak.Model.figures.Drawing;
import com.app.visualizationbreak.Model.figures.PNJ;
import com.app.visualizationbreak.Model.figures.Point;
import com.app.visualizationbreak.Model.figures.Tile;
import com.app.visualizationbreak.Model.ingameobjects.Farmer;
import com.app.visualizationbreak.Model.ingameobjects.Tower;
import com.app.visualizationbreak.Model.player.Player;
import com.app.visualizationbreak.Model.threads.LevelThread;

import java.io.IOException;
import java.util.ArrayList;

public class Level {
    private final int levelNum;
    private final int maxWaveNumber;
    private final int numberOfPaths;
    private final Party party ;
    private Wave currentWave ;
    private final ArrayList<Path> pathsForPNJ ;
    private final ArrayList<String> pathSequences ;
    private final ArrayList<Point> orPoints ;
    private final ArrayList<ArrayList<Point>> pathPoints ;
    private final ArrayList<Tile> mapTiles ;      // La liste des cases à ajouter sur le Drawing
    private final ArrayList<PNJ> alivePNJs ;   // Liste des PNJ en vie, indique si le niveau est fini ou non
    private final ArrayList<Tower> myTowers ;     // Liste des tours que le joueur a placé sur la map pour ce niveau
    private final ArrayList<Farmer> myFarmers;
    private final MapFile levelMap;               // Fichier qui contient les informations sur la map
    private final LevelThread levelThread;
    private Drawing drawing ;               // Chaque Level est associé à un Drawing à dessiner.
    private boolean finished = true ;       // Le niveau n'est pas commencé quand il est initié
    private boolean running = true ;

    /* une map différente sera chargée selon le mode de jeu ! plusieurs possibilités :
    1) regular
    2) free
    3) freemode1, 2, ou ... jusque 6
     */
    public Level(int levelNum, Party party, String gameMode) throws Exception {
        this.party = party ;
        myTowers = new ArrayList<>() ;
        myFarmers = new ArrayList<>();
        alivePNJs = new ArrayList<>();
        if(levelNum > Party.getInstance().getMaxLevelNumber()){
            throw new Exception("Level number out of bounds") ;
        }
        else{
            this.levelNum = levelNum;
        }
        this.maxWaveNumber = levelNum + 3;
        switch (gameMode){
            case "regular" :
                this.levelMap = new MapFile("src\\main\\java\\com\\app\\visualizationbreak\\Model\\Maps\\map" + levelNum + ".txt");
                //this.levelMap = new MapFile("src\\MapTriviales\\map" + levelNum + ".txt"); //pour les tests, niveaux triviaux
                break;

            case "free":
                this.levelMap = new MapFile("src\\main\\java\\com\\app\\visualizationbreak\\Model\\Maps\\FreeModeMap.txt");
                break;

            default:
                this.levelMap = new MapFile("src\\main\\java\\com\\app\\visualizationbreak\\Model\\Maps\\map" + gameMode.charAt(gameMode.length()-1)+".txt");
                Player.getInstance().getResources().addMoney(((int) gameMode.charAt(gameMode.length() - 1))*10);
        }

        // compte le nombre de chemin pour savoir sur cb de chemins les PNJ vont se répartir
        this.numberOfPaths = levelMap.countPaths() ;
        mapTiles = levelMap.makeMapTiles(party.getTilesSize()) ;
        pathSequences = levelMap.makePathSequences() ;
        pathsForPNJ = new ArrayList<>();
        pathPoints = new ArrayList<>() ;
        orPoints = new ArrayList<>() ;

        // création d'une liste de Path ainsi que d'une liste de points correspondants et une liste de points d'origine
        // où balancer les PNJ
        for(int i = 0; i< numberOfPaths; i++){
            pathsForPNJ.add(new Path(this, i)) ;
            pathPoints.add(pathsForPNJ.get(i).analyseSequence()) ;
            orPoints.add(pathPoints.get(i).get(0)) ;
        }

        levelThread = new LevelThread(this);
    }


    public void startLevel(){
        levelThread.start();
        finished = false ;
    }

    public Wave createWave(int i) throws Exception {
        currentWave = new Wave(this, i) ;
        currentWave.launchWave();
        return currentWave;
    }

    /** creation d'une tour partie 2 (partie 1 : drawing) **/
    public Tower createTower(String type, Point p) throws Exception {
        return TowerFactory.getInstance(type, p, this) ;
    }

    public Farmer createFarmer(String type, Point origin) throws Exception {
        return FarmerFactory.createFarmer(type,origin,this);
    }

    public void addTower(Tower tower) {
        drawing.addTower(tower);
        this.myTowers.add(tower);
    }

    public void addFarmer(Farmer farmer) {
        drawing.addFarmer(farmer);
        this.myFarmers.add(farmer);
    }

    public void addPNJ(PNJ pnj) {
        drawing.addMovingPNJ(pnj);
        this.alivePNJs.add(pnj);
    }

    public void removeTower(Tile tile, Tower tower) throws IOException {
        myTowers.remove(tower);
        drawing.removeTower(tile, tower);
        tower.remove();
    }

    public void removeFarmer(Tile tile, Farmer farmer) throws IOException {
        myFarmers.remove(farmer);
        drawing.removeFarmer(tile, farmer);
        farmer.remove();
    }

    public void removePNJ(PNJ pnj){
        alivePNJs.remove(pnj);
    }

    public ArrayList<Integer> firstTileLoc() throws IOException { return levelMap.firstTileLocs(); }

    public Drawing getDrawing(){return drawing ;}

    public void setDrawing(Drawing drawing){this.drawing = drawing ; }

    // accesseurs
    public ArrayList<Path> getPathsForPNJ() {return this.pathsForPNJ;}
    public ArrayList<PNJ> getPNJs() {return this.alivePNJs;}
    public ArrayList<String> getPathSequences(){return pathSequences;}
    public Party getParty() {return party ;}
    public int getWaveNumber() {
        if(currentWave!= null){
            return currentWave.getWavenumber();
        }
        else{
            return 0 ;
        }
    }
    public ArrayList<Tower> getMyTowers(){return myTowers;}
    public int getMaxWaveNumber() {return this.maxWaveNumber;}
    public ArrayList<ArrayList<Point>> getPathPoints() {
        return this.pathPoints;
    }
    public ArrayList<Tile> getMapTiles(){return mapTiles ;}
    public ArrayList<Point> getOrPoints() {return this.orPoints;}
    public int getNumber() {
        return this.levelNum;
    }
    public MapFile getMapFile() {
        return this.levelMap;
    }
    public int getNumberOfPaths(){return numberOfPaths;}


    // gestion de l'état du niveau
    public boolean isFinished(){return finished ;}
    public boolean isRunning() {return running; }
    public void finish() throws IOException {
        Party.getInstance().finishedLevel();
        finished = true ;
        running = false;
    }
    public void setState(boolean state){finished = state ;}


}