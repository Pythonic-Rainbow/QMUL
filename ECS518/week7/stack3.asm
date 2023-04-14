0000000000001300 <getStackPointer>:
    1300:	f3 0f 1e fa          	endbr64 
    1304:	55                   	push   %rbp
    1305:	48 89 e5             	mov    %rsp,%rbp
    1308:	48 89 e8             	mov    %rbp,%rax
    130b:	5d                   	pop    %rbp
    130c:	c3                   	ret    
    130d:	0f 1f 00             	nopl   (%rax)

0000000000001310 <f>:
    1310:	f3 0f 1e fa          	endbr64 
    1314:	55                   	push   %rbp
    1315:	48 89 e5             	mov    %rsp,%rbp
    1318:	41 56                	push   %r14
    131a:	48 89 e8             	mov    %rbp,%rax
    131d:	41 55                	push   %r13
    131f:	41 54                	push   %r12
    1321:	53                   	push   %rbx
    1322:	48 83 ff 01          	cmp    $0x1,%rdi
    1326:	74 50                	je     1378 <f+0x68>
    1328:	48 89 eb             	mov    %rbp,%rbx
    132b:	48 83 ff 02          	cmp    $0x2,%rdi
    132f:	74 40                	je     1371 <f+0x61>
    1331:	49 89 ec             	mov    %rbp,%r12
    1334:	48 83 ff 03          	cmp    $0x3,%rdi
    1338:	74 30                	je     136a <f+0x5a>
    133a:	49 89 ed             	mov    %rbp,%r13
    133d:	48 83 ff 04          	cmp    $0x4,%rdi
    1341:	74 20                	je     1363 <f+0x53>
    1343:	49 89 ee             	mov    %rbp,%r14
    1346:	48 83 ff 05          	cmp    $0x5,%rdi
    134a:	74 10                	je     135c <f+0x4c>
    134c:	48 83 ef 05          	sub    $0x5,%rdi
    1350:	e8 bb ff ff ff       	call   1310 <f>
    1355:	48 39 c5             	cmp    %rax,%rbp
    1358:	48 0f 4e c5          	cmovle %rbp,%rax
    135c:	49 39 c6             	cmp    %rax,%r14
    135f:	49 0f 4e c6          	cmovle %r14,%rax
    1363:	49 39 c5             	cmp    %rax,%r13
    1366:	49 0f 4e c5          	cmovle %r13,%rax
    136a:	49 39 c4             	cmp    %rax,%r12
    136d:	49 0f 4e c4          	cmovle %r12,%rax
    1371:	48 39 c3             	cmp    %rax,%rbx
    1374:	48 0f 4e c3          	cmovle %rbx,%rax
    1378:	5b                   	pop    %rbx
    1379:	41 5c                	pop    %r12
    137b:	41 5d                	pop    %r13
    137d:	41 5e                	pop    %r14
    137f:	5d                   	pop    %rbp
    1380:	c3                   	ret    