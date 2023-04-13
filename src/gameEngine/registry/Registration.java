package gameEngine.registry;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import gameEngine.registry.base.Deposit;
import gameEngine.registry.base.ItemBase;
import gameEngine.registry.base.TileBase;
import gameEngine.registry.deposits.*;
import gameEngine.registry.items.*;
import gameEngine.registry.tiles.*;
import world.Item;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;

public class Registration {
    public static HashMap<String, RegistryObject> registered = new HashMap<>();

    // TILES REGISTRATION
    public static RegistryObject<AutoCrafter> autoCrafter;
    public static RegistryObject<ElectricDrill1> electricDrill1;
    public static RegistryObject<ElectricDrill2> electricDrill2;
    public static RegistryObject<ElectricPole> electricPole;
    public static RegistryObject<ElectricExtractor1> electricExtractor1;
    public static RegistryObject<ElectricExtractor2> electricExtractor2;
    public static RegistryObject<ElectricFurnace> electricFurnace;

    public static RegistryObject<Furnace> furnace;
    public static RegistryObject<GoldChest> goldChest;
    public static RegistryObject<GoldTank> goldTank;
    public static RegistryObject<Grass> grass;
    public static RegistryObject<IronChest> ironChest;
    public static RegistryObject<IronTank> ironTank;
    public static RegistryObject<Pipe> pipe;
    public static RegistryObject<Belt> belt;
    public static RegistryObject<PetrolPowerPlant> petrolPowerPlant;
    public static RegistryObject<Pump> pump;
    public static RegistryObject<Refinery> refinery;
    public static RegistryObject<Robot> robot = registerObject(new Robot("robot"));
    public static RegistryObject<Sand> sand;
    public static RegistryObject<ThermalDrill> thermalDrill;
    public static RegistryObject<ThermalPowerPlant> thermalPowerPlant;
    public static RegistryObject<Water> water;
    public static RegistryObject<Windmill> windmill;
    public static RegistryObject<ThermalArm> thermalArm;


    // DEPOSIT REGISTRATION

    public static RegistryObject<CoalDeposit> coalDeposit;
    public static RegistryObject<IronDeposit> ironDeposit;
    public static RegistryObject<CopperDeposit> copperDeposit;
    public static RegistryObject<StoneDeposit> stoneDeposit;
    public static RegistryObject<PetrolDeposit> petrolDeposit;
    public static RegistryObject<GoldDeposit> goldDeposit;

    //ITEMS REGISTRATION

    public static RegistryObject<Coal> coal;
    public static RegistryObject<Coil> coil;
    public static RegistryObject<CopperIngot> copperIngot;
    public static RegistryObject<CopperOre> copperOre;
    public static RegistryObject<ElectronicCircuit> electronicCircuit;
    public static RegistryObject<Engine> engine;
    public static RegistryObject<Gear> gear;
    public static RegistryObject<GoldIngot> goldIngot;
    public static RegistryObject<GoldOre> goldOre;
    public static RegistryObject<IronIngot> ironIngot;
    public static RegistryObject<IronOre> ironOre;
    public static RegistryObject<IronStick> ironStick;
    public static RegistryObject<Plastic> plastic;
    public static RegistryObject<Petrol> petrol;
    public static RegistryObject<Steel> steel;
    public static RegistryObject<StoneBrick> stoneBrick;
    public static RegistryObject<StoneOre> stoneOre;

    public static final int REGISTRY_COUNT = 47;
    public static int loaded = 0;

    public static void beginRegistration(){
        autoCrafter = registerObject(new AutoCrafter("auto_crafter"));
        electricDrill1 = registerObject(new ElectricDrill1("electric_drill1"));
        electricDrill2 = registerObject(new ElectricDrill2("electric_drill2"));
        electricPole = registerObject(new ElectricPole("electric_pole"));
        electricExtractor1 = registerObject(new ElectricExtractor1("electric_extractor1"));
        electricExtractor2 = registerObject(new ElectricExtractor2("electric_extractor2"));
        electricFurnace = registerObject(new ElectricFurnace("electric_furnace"));
        furnace = registerObject(new Furnace("furnace"));
        goldChest = registerObject(new GoldChest("gold_chest"));
        goldTank =registerObject(new GoldTank( "gold_tank"));
        grass = registerObject(new Grass("grass"));
        ironChest = registerObject(new IronChest("iron_chest"));
        ironTank = registerObject(new IronTank("iron_tank"));
        pipe = registerObject(new Pipe("pipe"));
        belt = registerObject(new Belt("belt"));
        petrolPowerPlant = registerObject(new PetrolPowerPlant("petrol_power_plant"));
        pump = registerObject(new Pump("pump"));
        refinery = registerObject(new Refinery("refinery"));
        sand = registerObject(new Sand("sand"));
        thermalDrill = registerObject(new ThermalDrill("thermal_drill"));
        thermalPowerPlant = registerObject(new ThermalPowerPlant("thermal_power_plant"));
        water = registerObject(new Water("water"));
        windmill = registerObject(new Windmill("windmill"));
        coalDeposit = registerObject(new CoalDeposit("coal_deposit"));
        ironDeposit = registerObject(new IronDeposit("iron_deposit"));
        copperDeposit = registerObject(new CopperDeposit("copper_deposit"));
        stoneDeposit = registerObject(new StoneDeposit("stone_deposit"));
        petrolDeposit = registerObject(new PetrolDeposit("petrol_deposit"));
        goldDeposit = registerObject(new GoldDeposit("gold_deposit"));
        coal = registerObject(new Coal("coal"));
        coil = registerObject(new Coil("coil"));
        copperIngot = registerObject(new CopperIngot("copper_ingot"));
        copperOre = registerObject(new CopperOre("copper_ore"));
        electronicCircuit = registerObject(new ElectronicCircuit("electronic_circuit"));
        engine = registerObject(new Engine("engine"));
        gear = registerObject(new Gear("gear"));
        goldIngot = registerObject(new GoldIngot("gold_ingot"));
        goldOre = registerObject(new GoldOre("gold_ore"));
        ironIngot = registerObject(new IronIngot("iron_ingot"));
        ironOre = registerObject(new IronOre("iron_ore"));
        ironStick = registerObject(new IronStick("iron_stick"));
        plastic = registerObject(new Plastic("plastic"));
        petrol = registerObject(new Petrol("petrol"));
        steel = registerObject(new Steel("steel"));
        stoneBrick = registerObject(new StoneBrick("stone_brick"));
        stoneOre = registerObject(new StoneOre("stone_ore"));
        thermalArm = registerObject(new ThermalArm("arm"));
    }

    private static <T extends Registrable> RegistryObject<T> registerObject(T object){
        RegistryObject<T> toReturn = new RegistryObject<>(object);
        registered.put(object.getRegistryName(), toReturn);
        loaded++;
//        System.out.println("Progression: "+((loaded*100)/REGISTRY_COUNT)+"%");
        return toReturn;
    }

    public static <T extends Registrable> RegistryObject<T> getRegistryObjectByRegistryName(String registryName){
        if (registered.containsKey(registryName)){
            return registered.get(registryName);
        }
        return null;
    }

    public static void registerCrafts(File path, BiConsumer<Item, List<Item>> consumer){
        String content = null;
        try {
            byte[] encoded = Files.readAllBytes(path.toPath());
            content = new String(encoded, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(content);
        JsonParser parser = new JsonParser();
        JsonObject object = (JsonObject) parser.parse(content);
        object.keySet().forEach((k) -> {
            System.out.println("k = " + k);
            try {
                Registrable registrable = getRegistryObjectByRegistryName(k).get();
                ItemBase itemBase = null;
                if (registrable instanceof ItemBase base) {
                    itemBase = base;
                } else if (registrable instanceof TileBase base) {
                    itemBase = base.getItem();
                }
                JsonObject craftContent = object.get(k).getAsJsonObject();
                JsonObject ingredients = craftContent.get("ingredients").getAsJsonObject();
                List<Item> ingrList = new ArrayList<>();
                ingredients.entrySet().forEach((data) -> {
                    Registrable registrable1 = getRegistryObjectByRegistryName(data.getKey()).get();
                    if (registrable1 instanceof TileBase base) {
                        ingrList.add(new Item(base.getItem(), data.getValue().getAsInt()));
                    } else if (registrable1 instanceof ItemBase base) {
                        ingrList.add(new Item(base, data.getValue().getAsInt()));
                    } else if (registrable1 instanceof Deposit deposit) {
                        ingrList.add(new Item(deposit.getLoot(), data.getValue().getAsInt()));
                    }
                });
                consumer.accept(new Item(itemBase, craftContent.get("amount").getAsInt()), ingrList);
            } catch (Exception e){
                e.printStackTrace();
            }
        });
    }
}
