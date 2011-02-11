function process(myCar){
    if(myCar.isNotMine()){   
         return;             
    }
    car.paint("red");
    car.changeWheel();
    while(car.hasGazol() && car.getDriver().isNotStressed()){
         car.drive();
    }
    return;
}
