package issia23.agents;

import issia23.behaviours.CafeRepondreUtilisateur;
import issia23.behaviours.SparePartsStoreRepondreUtilisateur;
import issia23.data.Part;
import issia23.data.ProductType;
import jade.core.AgentServicesTools;
import jade.gui.AgentWindowed;
import jade.gui.SimpleWindow4Agent;
import jade.lang.acl.MessageTemplate;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class SparePartsStoreAgent extends AgentWindowed {
    public List<Part> parts;
    public ProductType productType;

    @Override
    public void setup(){
        this.window = new SimpleWindow4Agent(getLocalName(),this);
        this.window.setBackgroundTextColor(Color.cyan);
        productType = ProductType.getRandomProductType();
        println("hello, do you want a piece of something ?");

        //registration to the yellow pages (Directory Facilitator Agent)
        AgentServicesTools.register(this, "repair", "SparePartsStore");
        println("I'm just registered as a Spare Parts Store for " + productType.name());

        //distributors have 3 examples of some parts
        parts = new ArrayList<>();
        var allParts = Part.getPartsWithFilter(part -> part.getName().contains(productType.name())
                && part.getDifficulty() < 4
                && part.getPrice() <= 40
                && part.getPrice() >= 20);
        var nb = allParts.size();
        for(int i=0; i<10 && i < nb; i++) {
            var rand = (int)(Math.random()*nb);
            parts.add(allParts.get(rand));
        }
        println("i have the following parts : ");
        for(var p:parts) println(p.getName() + " ");

        addListeningACFP();
    }

    public Part findPart(String partName) {
        for (Part part : parts) {
            if (part.getName().equals(partName)) {
                return part;
            }
        }
        return null;
    }

    private void addListeningACFP()
    {

        MessageTemplate model = MessageTemplate.MatchConversationId("id");

        var attenteDemandeUtilisateur = new SparePartsStoreRepondreUtilisateur(this, model);

        addBehaviour(attenteDemandeUtilisateur);
    }


    public void println(String s){
        window.println(s);
    }

}
