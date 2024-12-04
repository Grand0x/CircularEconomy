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

public class DistributorAgent extends AgentWindowed {
    @Override
    public void setup(){
        this.window = new SimpleWindow4Agent(getLocalName(),this);
        this.window.setBackgroundTextColor(Color.GRAY);
        println("hello, do you want a new object ?");

        // Registration to the yellow pages (Directory Facilitator Agent)
        AgentServicesTools.register(this, "repair", "distributor");
        println("I'm just registered as a Distributor");

        addListeningACFP(); // Add behavior to handle CFP
    }

    private void addListeningACFP() {
        // Template for CFP messages
        MessageTemplate model = MessageTemplate.MatchConversationId("id");

        // Behavior to handle CFP messages
        ContractNetResponder behaviour = new ContractNetResponder(this, model) {
            @Override
            protected ACLMessage handleCfp(ACLMessage cfp) throws RefuseException, FailureException, NotUnderstoodException {
                println(cfp.getSender().getLocalName() + " is asking for a replacement: " + cfp.getContent());
                ACLMessage reply = cfp.createReply();

                int cost = 60; // Fixed cost for distributor
                int time = 1;  // Fixed time for distributor
                reply.setPerformative(ACLMessage.PROPOSE);
                reply.setContent(cost + "," + time); // Proposal includes cost and time
                println("Proposing replacement with cost: " + cost + "€ and time: " + time + " day.");
                return reply;
            }

            @Override
            protected ACLMessage handleAcceptProposal(ACLMessage cfp, ACLMessage propose, ACLMessage accept) throws FailureException {
                println("Proposal accepted by " + cfp.getSender().getLocalName());
                ACLMessage reply = accept.createReply();
                reply.setPerformative(ACLMessage.INFORM);
                reply.setContent("Replacement delivered!");
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
