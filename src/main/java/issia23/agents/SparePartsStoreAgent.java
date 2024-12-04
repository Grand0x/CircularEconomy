package issia23.agents;

import jade.core.AgentServicesTools;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.gui.AgentWindowed;
import jade.gui.SimpleWindow4Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.ContractNetResponder;


import java.awt.*;

public class SparePartsStoreAgent extends AgentWindowed {
    @Override
    public void setup(){
        this.window = new SimpleWindow4Agent(getLocalName(),this);
        this.window.setBackgroundTextColor(Color.cyan);
        println("hello, do you want a piece of something ?");

        // Registration to the yellow pages (Directory Facilitator Agent)
        AgentServicesTools.register(this, "repair", "SparePartsStore");
        println("I'm just registered as a Spare Parts Store");

        addListeningACFP(); // Add behavior to handle CFP
    }

    private void addListeningACFP() {
        // Template for CFP messages
        MessageTemplate model = MessageTemplate.MatchConversationId("id");

        // Behavior to handle CFP messages
        ContractNetResponder behaviour = new ContractNetResponder(this, model) {
            @Override
            protected ACLMessage handleCfp(ACLMessage cfp) throws RefuseException, FailureException, NotUnderstoodException {
                println(cfp.getSender().getLocalName() + " is asking for a spare part: " + cfp.getContent());
                ACLMessage reply = cfp.createReply();

                // Simulate stock availability (70% chance of having the part)
                if (Math.random() > 0.3) {
                    int cost = 30; // Fixed cost for spare parts store
                    int time = 1;  // Fixed time for spare parts store
                    reply.setPerformative(ACLMessage.PROPOSE);
                    reply.setContent(cost + "," + time); // Proposal includes cost and time
                    println("Proposing spare part with cost: " + cost + "€ and time: " + time + " day.");
                } else {
                    reply.setPerformative(ACLMessage.REFUSE);
                    println("Refusing request: part not available.");
                }
                return reply;
            }

            @Override
            protected ACLMessage handleAcceptProposal(ACLMessage cfp, ACLMessage propose, ACLMessage accept) throws FailureException {
                println("Proposal accepted by " + cfp.getSender().getLocalName());
                ACLMessage reply = accept.createReply();
                reply.setPerformative(ACLMessage.INFORM);
                reply.setContent("Part delivered!");
                return reply;
            }

            @Override
            protected void handleRejectProposal(ACLMessage cfp, ACLMessage propose, ACLMessage reject) {
                println("Proposal rejected by " + cfp.getSender().getLocalName());
            }
        };

        addBehaviour(behaviour);
    }
}