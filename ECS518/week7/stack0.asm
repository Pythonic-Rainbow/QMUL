0000000000001189 <getStackPointer>:
    1189:	f3 0f 1e fa          	endbr64 
    118d:	55                   	push   %rbp
    118e:	48 89 e5             	mov    %rsp,%rbp
    1191:	48 89 e8             	mov    %rbp,%rax
    1194:	5d                   	pop    %rbp
    1195:	c3                   	ret    

0000000000001196 <f>:
    1196:	f3 0f 1e fa          	endbr64 
    119a:	55                   	push   %rbp
    119b:	48 89 e5             	mov    %rsp,%rbp
    119e:	48 83 ec 20          	sub    $0x20,%rsp
    11a2:	48 89 7d e8          	mov    %rdi,-0x18(%rbp)
    11a6:	48 83 7d e8 01       	cmpq   $0x1,-0x18(%rbp)
    11ab:	75 0c                	jne    11b9 <f+0x23>
    11ad:	b8 00 00 00 00       	mov    $0x0,%eax
    11b2:	e8 d2 ff ff ff       	call   1189 <getStackPointer>
    11b7:	eb 36                	jmp    11ef <f+0x59>
    11b9:	b8 00 00 00 00       	mov    $0x0,%eax
    11be:	e8 c6 ff ff ff       	call   1189 <getStackPointer>
    11c3:	48 89 45 f0          	mov    %rax,-0x10(%rbp)
    11c7:	48 8b 45 e8          	mov    -0x18(%rbp),%rax
    11cb:	48 83 e8 01          	sub    $0x1,%rax
    11cf:	48 89 c7             	mov    %rax,%rdi
    11d2:	e8 bf ff ff ff       	call   1196 <f>
    11d7:	48 89 45 f8          	mov    %rax,-0x8(%rbp)
    11db:	48 8b 45 f8          	mov    -0x8(%rbp),%rax
    11df:	48 3b 45 f0          	cmp    -0x10(%rbp),%rax
    11e3:	7d 06                	jge    11eb <f+0x55>
    11e5:	48 8b 45 f8          	mov    -0x8(%rbp),%rax
    11e9:	eb 04                	jmp    11ef <f+0x59>
    11eb:	48 8b 45 f0          	mov    -0x10(%rbp),%rax
    11ef:	c9                   	leave  
    11f0:	c3                   	ret  


main 0x7fffffffe1d0
in f: 0x7fffffffe1c8 (-8)
push %rbp: 0x7fffffffe1c0 (-8)
sub 0x20: 0x7fffffffe1a0 (-32)
1st getSP(): rax -> e190

in f: 0x7fffffffe1c8
push %rbp: 0x7fffffffe1c0 (-8)
sub 0x20: 0x7fffffffe1a0 (-32)
call getStackPointer: 0x7fffffffe198 (-8)
