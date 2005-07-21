/*
 * Copyright 2005 Anders Nyman.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.sf.j2ep;

import javax.servlet.http.HttpServletRequest;


/**
 * The Rule interface is implemented by
 * the rules for the reverse proxy.
 * Based on the work by Yoav Shapira 
 * for the balancer webapp.
 *
 * @author Anders Nyman
 */
public interface Rule {
    
    /**
     * Determine if the given request
     * matches the rule.
     *
     * @param request The request
     * @return boolean True if matches, will be redirected.
     */
    boolean matches(HttpServletRequest request);

    /**
     * Returns the redirect URI for
     * requests that match this rule.
     * Process is used in order to let the rules
     * to do rewriting of the URI before beeing handled
     * by the server.
     * 
     * @param uri URI to be processed
     * @return String The final URI
     */
    String process(String uri);
    
    /**
     * Returns the reverted URI, this means�
     * that if a URI is processed and then reverted
     * it should be the same URI. That means 
     * revert(process("some random string)) should
     * return 'some random string'.
     * 
     * @param uri URI to be reverted
     * @return String The reverted URI
     */
    String revert(String uri);
    
    /**
     * Returns the server this rule is mapped to.
     * Use this to get the server and issue a connection.
     * 
     * @return Server The server
     */
    Server getServer();
    
    /**
     * Used to specify which server this rule is linked to.
     * 
     * @param server The server to store
     */
    void setServer(Server server);
    
    /**
     * Returns the server id for the server connected with this rule.
     * This should only be used when creating rules from a XML mapping,
     * on other circumstances use the setServer instead.
     * 
     * @return String The server id
     */
    String getServerId();
    

    
    /** 
     * TODO maybe not that usefull even tough I like a NullObject more then 
     * checking for null. The problem is that I will have to check for this anyways
     * since Rule.NULL can't handel any requests. Will the use of a possible
     * Server.NULL work?, if so this should stay. 
     */
    public static final Rule NULL = new Rule() {

        public boolean matches(HttpServletRequest request) {
            return false;
        }

        public String process(String uri){
            return uri;
        }
        
        public String revert(String uri){
            return uri;
        }
        
        public String getServerId() {
            return "";
        }
        
        public void setServer(Server server) {
            
        }
        
        public Server getServer() {
            return null;
        }
    };
}