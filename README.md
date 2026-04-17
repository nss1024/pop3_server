Java POP3 Server (RFC 1939)

A lightweight POP3 server implemented in Java from scratch using TCP sockets, thread pools, and a command-pattern architecture.

This project was built as a protocol and systems engineering exercise to understand how mailbox retrieval servers work internally rather than relying on existing frameworks.

Features
RFC 1939 style POP3 session flow
USER / PASS authentication flow
Mailbox locking (single active session per mailbox)
File-backed mailbox storage
Message listing (STAT, LIST, UIDL)
Message retrieval (RETR, TOP)
Message deletion lifecycle (DELE, RSET, QUIT)
NOOP support
Streaming RETR responses for efficient large message delivery
Telnet-testable interface
Architecture
Client
  ↓
Pop3Server (accept loop)
  ↓
Worker Thread Pool
  ↓
Session Handler
  ↓
Command Registry
  ↓
Mailbox Services / Lock Manager
  ↓
Filesystem Mailboxes
POP3 Session States
AUTHORIZATION
→ TRANSACTION
→ UPDATE (QUIT)
Example Usage
telnet localhost 8110

USER bob
PASS secret
STAT
LIST
RETR 1
QUIT
Purpose of the Project

This project was created to gain hands-on experience with:

TCP server design
Protocol state machines
Command pattern architecture
File-backed persistence
Session locking / concurrency control
Streaming network responses
Implementing an RFC from specification
