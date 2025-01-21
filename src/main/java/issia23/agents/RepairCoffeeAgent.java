package issia23.agents;

import issia23.data.Part;
import jade.core.AgentServicesTools;
import jade.gui.AgentWindowed;
import jade.gui.SimpleWindow4Agent;
import jade.lang.acl.MessageTemplate;

import issia23.behaviours.CafeRepondreUtilisateur;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class RepairCoffeeAgent extends AgentWindowed {
    public List<Part> parts;


    @Override
    public void setup(){
        this.window = new SimpleWindow4Agent(getLocalName(),this);
        this.window.setBackgroundTextColor(Color.orange);
        println("hello, do you want coffee ?");

        //registration to the yellow pages (Directory Facilitator Agent)
        AgentServicesTools.register(this, "repair", "coffee");
        println("I'm just registered as a repair-coffee");

        //distributors have 2 examples of some parts
        parts = new ArrayList<>();
        var allParts = Part.getPartsWithFilter(part -> part.getDifficulty() < 4 && part.getPrice() <= 15);
        var nb = allParts.size();
        for(int i=0; i<5; i++) {
            var rand = (int)(Math.random()*nb);
            parts.add(allParts.get(rand));
        }
        println("i have the following parts : ");
        for(var p:parts) println(p.getName() + " ");


        addListeningACFP();

    }

    private void addListeningACFP()
    {

        MessageTemplate model = MessageTemplate.MatchConversationId("id");

        var attenteDemandeUtilisateur = new CafeRepondreUtilisateur(this, model);

        addBehaviour(attenteDemandeUtilisateur);
    }

    public Part findPart(String partName) {
        for (Part part : parts) {
            if (part.getName().equals(partName)) {
                return part;
            }
        }
        return null;
    }

    public void println(String s){
        window.println(s);
    }

}
